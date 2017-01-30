# Map collection for MCPE(Android)


## Technologies used:
- **Rx** - for handling parrallel and async tasks ( server calls and database operations)._  
- **realm** - as a database `model-layer` _(hosted **locally** on device as a set of tables in text files)._
- **backendless** - also `model-layer` _(hosted **remotely** on backendless server as set of tables)._
- **retrolambda** - for adding Java8 lambdas compatible with 1.6, 1.7.

### Detailed review of implementation can be found here:
- [Project Wiki]()
- [GitHub Project](https://github.com/ffive/mcpe-maps-mvp/projects/1)
- [Project Page on GitHub Pages](https://ffive.github.io/mcpe-maps-mvp)

## This project uses moxy library as MVP basis.

### MVP refers to an abstract project architecture narrowed down to the relations and communicating  between `Model`, `View` ,`Presenter`

![](https://camo.githubusercontent.com/d0a4baaa8261d93d56367a0d82f3be91abdd95bf/68747470733a2f2f686162726173746f726167652e6f72672f66696c65732f6132652f6235312f3862342f61326562353138623436356134646639623437653638373934353139323730642e676966)

Don't be scared of this squares. Imagine yourself in automatic-order Cafe.

Then:

- `Models` include kitchen,fridge,coffee machine,billing system, vip members list, etc...
- `ViewState` role goes to **Waiter**.
- `View` is just a **set** of _all possible_ **interactions** cafe will perform 0+ times on each customer. 
- `Presenter` is an iPad with menu,orders,billing - it handles data processing between: 
  - customers (`View` == anyone who implements `CustomerView`'s methods)
  - waiter (`ViewState`);
  - kitchen,fridge,coffee machine,billing system, vip members list, etc... (`Model`)
  
>`presenter` deliveres results of cooking (cafe commands) to you with the help of **Waiter** .
 
    
#### Important!
>Please think of `View` it **in terms of _the system designer's vision_** of the process._That kind of meaning. Not the synonym of 'look' or 'widget'_.	

### Implementation details:
#### View:
	public interface CustomerView implements MvpView {				
		void welcome(String greetingsPhrase);
		void assignSeat (int tableNumber);
		void presentMenu (List<MenuItems> menu);
		void orderReady(MenuItem cookedItem);
		void showBill(Map<MenuItem,String> chequeBiu);
		void auRevoir();
	}
  
  - Each and every **Customer**, including you,  `implements CustomerView` - defines _how this entity reacts_ to events which might happen in cafe (defined above^). 
  
>People are different. Cafe visitors are different. App users are different. But our **aim** is to find such _typical_ actions that _do not depend_ on the difference between customers/users. That's one of the most importance. Both visitor and organisation can use cafe : use menu, place an order, pay bill. And to succeed the process there is no difference for cafe how old is the organisation or what annual income the visitor has.

   - Let's define some more links between _cafe visit_ and _app usage_:
     - a cafe == MVP system (app+server+storage+users)
     - a customer ==  might:
     

? | Real Cafe | Activity connected to MVP system
------------ |	------------		| -------------
where?			|Starbucks  				| `? implements CustomerView`
subject			| a person, group, organisation 	| fragment, activity, custom
subject qualifier	|knows what to do in cafe 		| ~overrided~ implemented CustomerView with all it's methods
subject's reactions	| Changes of states `waiting,placing an order,eating,paying` | UI changes - displays text,images, 
subject's system independent actions 	| disappear in toilet, answer phone call, browsing memes |  calls, power off, connectivity issues, system events, app switches 

>The main rule is to allow cafe see a Customer-like behaviour described in Cafe's vision (`View`)  of a typical visitor.

### Multiple `views` attached to single `presenter`
  When you're not alone and came with a friend. Think of it as a perfect place to attach another customer to same iPad; It will easily handle same operations for second customer on the same table, independent of the fact, that your friends reaction will differ from yours; And properties (_age, gender, appettite) usually differs from yours. That's just doesn't play any role in MVP angle of this process.
        
 
## Let's look at your typical actions through the prism of Moxy-MVP:
  
1. You have an _initial state_ : your eyes render hungry, your whole body is running `performLongWaitAnimation()`
2. iPad (`presenter`) presents a menu: 

		//1. Customer chooses coffee and donut, iPad's perspective:
		-> onOrderReceived( List<MenuItem> orderedItems ) 
		-> start retrieving coffee from `model`:coffeemachine
		-> get donut from `model:fridge`
		-> get waiter and send donut to Customer with him
 
 - `presenter` begins `new Async( ()->launchCoffeeMachine("latte") )`,
 - `presenter` launches another  sync - `fridge.retrieveDonut()` ,
 - `presenter` listens to events:
   - from `model:coffeemachine` -  when its ready- iPad issues a command - tells waiter coffee to move coffee from `model:machine` to Customer;
   - from `CustomerView`callbacks - Customer performs Eating. When finished - CustomerView calls back to presenter to ask a bill:
	`mPresenter.onEatFinished();`
 

### The main aspect to keep in mind all the time - it is up to you to decide:
- What part of your to consider `model`s and `view`s
- Whether to fit particular code pieces to MVP _**at all**_;
  
>Mixing MVP and non-MVP *is already good*, while moving more code to MVP is good x10.


#### Nevertheless there are some tactics which allow to drastically reduce the volume of code needed to create a system _if and **only if**_ **you define the roles of your classes** according to some rules - let's call them [**Best practices**](https://github.com/ffive/mcpe-maps-mvp/wiki/Best-Practices)

## Model
`model` - is literally _anything_ where we can _read/write/get/set/download/upload/configure/change_ any digital **data**:
  
- online data input/output/storage:
  - retrofit
  - backendless
  - rest api's
- offline storage create/read/update/delete (**CRUD**):
  - realm
  - SQLite
  - SharedPreferences, etc...
- deivce services:
  - intents
  - services
  - geo
  - display
  - audio
  - camera, etc...
- sensors:
  - gyroscope
  - light sensor
  - accelerometer
  - microphone
  - compass
  - EMV sensor
  - wifi, Bluetooth, connected gadgets etc.

>if there is _something_ we can get data from - we want to use it as a `model-layer` in this architecture).
No specific MVP code is needed to consider any data source  a `model`. Use the same code you would use if you write non-MVP. **The only rule here is to interact with `model` from presenter! This will make the magic of commands history in ViewState**


## View
`view`- is an **`interface`** defining what action some entity (device screen, RelativeLayout, widget, sound device)  is meant to be able to perform.


### Steps to construct a View part of Moxy- MVP:
  
1. define interface (make it `implement MvpView`)

		public interface MyMoxyV extends MvpView{
			void showLoadingProgress();
			void beginIntroAnimation();
			void updateList(List<E> newList);
			void setVolume(float volumeDb);
			//well that's all - View is ready
		}
		
2. Now you take `Activity`,`Fragment` or any `CustomView+delegate` and add  `implements MyMoxyV` to make them be a 100% _View_ of _MVP_.   `@InjectPresenter   MvpPresenter myPresenterObjectInsideActivity;

>[example](https://github.com/ffive/mcpe-maps-mvp/blob/master/app/src/main/java/com/sopa/mvvc/ui/fragment/blank/UploadMapFragment.java#L22-L25)

		public class MyFragment extends MvpAppCompatFragment implements MyMoxyV {
		
			@InjectPresenter
			MyMoxyPresenter myPresenterObject;
			
			@Override
			public void onCreate{
			...
			}
			
			...
		}


The above @InjectPresenter annotation tells moxy to generate `ViewState` class for this `View` implementor.

>**Important** : `android.View` is totally different from `View` V of MVP  . Really. Not samesame kind of stuff. 

## ViewState

First, it is **generated automatically by Moxy** and **it works perfect**.

>@InjectsPresenter - generates ViewState.

ViewState is a class which :
  
- holds the current state of `view` and also history of commands from `presenter` to `views`.
- manages the activity/fragments lifecycle mess for you - and makes it perfectly.
  
## Presenter
Presenter is a java class which `implements MvpPresenter`
### Generally you'll find yourself writing 3 types of methods here:

1. Methods defining how you retrieve/save data from `model` (`model` == code talking to): 
  - [server](https://github.com/ffive/mcpe-maps-mvp/blob/master/app/src/main/java/com/sopa/mvvc/presentation/presenter/blank/UploadMapPresenter.java#L65-L77)
  - [database](https://github.com/ffive/mcpe-maps-mvp/blob/master/app/src/main/java/com/sopa/mvvc/presentation/presenter/blank/UploadMapPresenter.java#L36-L38)
  - files
  - camera
  - sensors
  - touchscreen
  
2. Methods to manipulate the data (so-called business logic of the app)
 
3. Callbacks - write methods you will call from **View**, when events( eg. clicks, touches, end of animation)  happen there.
	
`Presenter` methods examples:

- Type 1 (CRUD)
  - local
  
		private List<Map> getLocalMaps(){
		
			Model model = getLocalRepository(); 
			return model.where(Map.class).findAll();	 
	
		}
  - remote
  
		private refreshMaps(){
		
			Model model = Backendless.Persistence();
			model.of(Map.class)
				.where("objectId",map.id)
				.find( results -> { getLocalModel().copyOrupdate(results); });
		}
		  	
- Type 2 (logic):

		private Map incrementLikes(Map map){
			int oldLikes = map.getLikes();			// inner data manipulations
			map.setLikes( oldLikes + 1);
			return map;
		}
		
		onSettingsClickedFromActivity(){
			
			UserConfig currentSettings = model.getConfigs(); //retrieve up-to-date configs from db
		
			//View(State) We send the configs object to view;
			// View in it's turn has a databinded layout which takes userConfig 
			// and displays it's variables we took from db - that's the job of presenter as designed;
			getViewState().displaySettingsWindowUsingConfig(config); 	
		}

- type 3 (callbacks)
  - simple:
  
		onNewLevel(){
			getViewState().showSuccessAnimation();
			getViewState().displayAd();
		}
		
  - mixed: 
  
		onLikeButtonClicked(int mapId){		//type 3: this method is called from activity,(like btn ClickListener)
			
			getViewState().runLikeAnimation();		// ui command ( View's method)
			getViewState().showBackgroundProgress();	// ui command ( View's method)
			
			map = getLocalMaps().getById(mapId);		// type 1 - retrieving
			incrementLikes(map);				// type 2 - logic
		
			model.saveToDatabase(map);    			// type 1 - saving updated map to db 
			
			getViewState().hideBackgroundProgress();	// ui command ( View's method)
			
		}
	
 	
		
to be continued...

