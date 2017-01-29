# Map collection for MCPE(Android)

## This project uses moxy library as MVP basis.
### MVP refers to an abstract project architecture narrowed down to the relations and communicating  between `Model`, `View` ,`Presenter`.

![](https://camo.githubusercontent.com/d0a4baaa8261d93d56367a0d82f3be91abdd95bf/68747470733a2f2f686162726173746f726167652e6f72672f66696c65732f6132652f6235312f3862342f61326562353138623436356134646639623437653638373934353139323730642e676966)

more info can be found in [wiki/moxy MVP](https://github.com/ffive/mcpe-maps-mvp/wiki/Moxy-MVP)


#### Model
`Model-layer` literally _anything_ where we can _**read/write/get/set/download/upload/configure/change** any **digital data**_
- server
- database
- file
- camera
- sensors
- screen
if there is _something_ we can get data from - we want to use it as a `model-layer` in this architecture)

#### Presenter
Presenter is a single java class, holding methods of 2 kinds;
1. methods defining how smth is talking to.
		- http calls code
		- database code
		- deivce services
		- speech recognition
		- xml parsing
		- sensors code, etc...
2. methods (callbacks) for a view - here we write how presenter reacts to events happened in view.
.
.
```
//pseudo-code:

onLikeClickedInView(int mapId){

	likesOld = model.getLikesCount(mapId);  //type 1
	model.updateDatabase( likesOld + 1 );   //type 2
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

etc
```

- `view state` - ViewState is a class which controls communications between M,V and P  and **_is generated automatically by Moxy_**


- **_Rx_** - for handling parrallel and async tasks ( server calls and database operations)._  
- **realm** - as a database `model-layer` _(hosted **locally** on device as a set of tables in text files)._
- **backendless** - also `model-layer` _(hosted **remotely** on backendless server as set of tables)._
- **retrolambda** - for adding Java8 lambdas compatible with 1.6, 1.7.

###Detailed review of implementation can be found here:
	Project Wiki ...
	GitHub Project [can be found here](https://github.com/ffive/mcpe-maps-mvp/projects/1)
	Project Page [github pages](https://github.com/ffive/mcpe-maps-mvp/projects/1)
