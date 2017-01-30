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

- `Models` are kitchen,fridge,coffee machine,billing system, vip members list, etc...
- `View` is a set of _all possible_ actions. _Please think of it in terms of **The system creator's vision of the process. That kind of meaning. Not the synonym of 'look' or 'widget'**_.	
		
			public interface CustomerView implements MvpView {
		
  				void welcome(String greetingsPhrase);
  				void assignSeat (int tableNumber);
  				void presentMenu (List<MenuItems> menu);
				void orderReady(MenuItem cookedItem);
				void showBill(Map<MenuItem,String> chequeBiu);
				void auRevoir();
  			}
  
  - Every customer including you `implements CustomerView` - defines _how this entity reacts_ to events which might happen in cafe (defined above^). 
  
>People are different. Cafe visitors are different. App users are different. But our **aim** is to find such _typical_ actions that _do not depend_ on the difference between customers/users. That's one of the most importance.

  - Let's define some more links between _cafe visit_ and _app usage_:
    - a cafe == system (app+server+storage+users)
    - a customer == `Activity implements CustomerView` 
      - might be a human, dog, organisation etc. The only rule is be able to perform Customer behaviour.
      - has some state we can describe - emotion and look == UI widgets, sound devices, [fleshlight]
      - will react(behave) to cafe events defined in `View` _(again - think of it in sense of Cafe Owner's view on the whole process)_
      
    
    - await (might be not the only one in cafe queue)
    - change face view(emote) and position(sit,walk,leave)
    - eat, drink
    - disappear to toilet, hang not responding to waiter while  on phone
 
- iPadMenu is a `Presenter` and handles data processing between 
  - `Views` - customers
  - `models` - kitchen,fridge,coffee machine,billing system, vip members list, etc...
- The waiter is a `ViewState`
  - presenter deliveres results of cooking,counting etc to you via waiter.
  
- 

  
Let's look at your typical actions through the prism of Moxy-MVP:
  
You have some _initial state_, your eyes render hungry, your whole body is running `performLongWaitAnimation()`
`Presenter` defined some `callbacks` so he receives from `View`:

    - menu_item_4 - coffee
    - menu_item_2 - donut
    `onOrderReceived(List<MenuItem> orderedItems )`! -> get item from `model:coffeemachine`, get item from `model:fridge`
  
 - `presenter` begins `new AsyncTask("ask barista to make latte")`, and syncTask - fridge.retrieveDonut() , meanwhile sending command to yoUI -> relax and take your seat waiting for results:  faceView State changes to happiness  and you take a seat( Your)
  -`presenter` also listens to `model:barista` events - so when barista tells presenter coffee is ready - presenter takes coffee and issues a View command (implemented by you )   `send coffee to View`
  - In response - you are rendered even more happy - and perform EatingAnimation. When eating ends - you call back to presenter to ask a bill.
  
>more info in [wiki/moxy MVP](https://github.com/ffive/mcpe-maps-mvp/wiki/Moxy-MVP)

### The main aspect to keep in mind all the time - it is up to you to decide:
- What part of your to consider `model`s and `view`s
- Whether to fit particular code pieces to MVP _**at all**_;
  
>Mixing MVP and non-MVP *is already good*, while moving more code to MVP is even better.


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

## View
`view`- is an **`interface`** defining what action some entity (device screen, RelativeLayout, widget, sound device)  is meant to be able to perform.


Steps to construct a View part of Moxy- MVP:
  
1. define interface (make it `implement MvpView`)

		public interface MyMoxyV extends MvpView{
			void showLoadingProgress();
			void beginIntroAnimation();
			void updateList(List<E> newList);
			void setVolume(float volumeDb);
			//well that's all - View is ready
		}
		
2. Now you take `Activity`,`Fragment` or any `CustomView+delegate` and add  `implements MyMoxyV` to make them be a 100% _View_ of _MVP_.   `@InjectPresenter    MvpPresenter myPresenterObjectInsideActivity;`
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
		
end of story.

>**Important** : `android.View` is totally different from `View` V of MVP  . Really. Not samesame kind of stuff. 

## ViewState

Firstly, it **_is generated automatically by Moxy_** and it works.

ViewState is a class which :
  
- holds the current state of `view` and also history of _change ui_ commands from presenter.
- manages the activity/fragments lifecycle mess for you - and makes it perfectly.
  
## Presenter
Presenter is a java class which `implements MvpPresenter`
Generally you'll find yourself writing 3 types of methods here:

1. Methods defining how you retrieve/save data from `model` (`model` == code talking to): 
  - [server](https://github.com/ffive/mcpe-maps-mvp/blob/master/app/src/main/java/com/sopa/mvvc/presentation/presenter/blank/UploadMapPresenter.java#L65-L77)
  - [database](https://github.com/ffive/mcpe-maps-mvp/blob/master/app/src/main/java/com/sopa/mvvc/presentation/presenter/blank/UploadMapPresenter.java#L36-L38)
  - files
  - camera
  - sensors
  - touchscreen

      
2. Methods to manipulate the data (so-called business logic of the app)
 
3. Callbacks - write methods you will call from **View**, when events( eg. clicks, touches, end of animation)  happen there.
	
		//pseudo-code:
		
		/* model -  any kind of storage containg a table of Map.class objects ( realm,backendless,prefs,etc..)
		getViewState() - a handle for our View( activity, fragment, view, layout, list item, etc.)
		*/	
	
		onLikeButtonClicked(int mapId){		//type 3: this method is called from activity,(like btn ClickListener)
			
			getViewState().runLikeAnimation();		// ui command ( View's method)
			getViewState().showBackgroundProgress();	// ui command ( View's method)
			
			Map map = model.getMap(mapId); 		 	// type 1 load map from db by id (came from activity)
			
			int oldLikes = map.getLikes();			// type 2 data manipulations
			map.setLikes( oldLikes + 1);
		
			model.saveToDatabase(map);    			// type 1 saving updated map to db 
			
			getViewState().hideBackgroundProgress();	// ui command ( View's method)
			
		}
	
		onSettingsClickedFromActivity(){
			
			UserConfig currentSettings = model.getConfigs(); //retrieve up-to-date configs from db
		
			//View(State) We send the configs object to view;
			// View in it's turn has a databinded layout which takes userConfig 
			// and displays it's variables we took from db - that's the job of presenter as designed;
			getViewState().displaySettingsWindowUsingConfig(config); 	
		}
	
		onNewLevel(){
			getViewState().showSuccessAnimation();
			getViewState().displayAd();
		}
		
		etc...
	```

