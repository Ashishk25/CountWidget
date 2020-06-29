# CountWidget
Android widget for select no of items. Usefull in situations like shopping cart , orders etc

<img src=https://github.com/Ashishk25/CountWidget/blob/master/Screenshot_20200629-222623.jpg height="300" width="200">
<img src=https://github.com/Ashishk25/CountWidget/blob/master/Screenshot_20200629-222628.jpg height="300" width="200">

## add this to your build.gradle app

```
dependencies {

	        implementation 'com.github.Ashishk25:CountWidget:1.1'
	}
```  
## or

```  
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```  

## Usage

For Working implementation of this library check ElegantNumberButtonSample App 
 * Just include the view as you do with any android widget.
 * Implement it in your java code as anyother widget.
 * To get the number simply call `getNumber()` method on the button object. 
 * To get the number from the button as soon as the button is clicked add a `setOnClickListener` to the view.
   
    ```java
    CountWidget widget = (CountWidget) findViewById(R.id.button);
    widget.setOnClickListener(new CountWidget.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = widget.getNumber();
            }
        });
    ```
 * use a valueChangeListener to listen for changes in value.
    
    ```java
    widget.setOnValueChangeListener(new CountWidget.OnValueChangeListener() {
            @Override
            public void onValueChange(CountWidget view, double oldValue, double newValue) {
            
            }
        });
     ```

## Methods

`setNumber(Integer number)`: Update the number of the widget. 

`setRange(Integer startNumber, Integer finalNumber)` : Set the operational range for the widget

`setOnValueChangeListener(OnValueChangedListener listener)`: listen for changes in the value

## Disclaimer
This is a modification to ElegantNumberButton repo -https://github.com/ashik94vc/ElegantNumberButton
