### Overview

wiseWeather is a simple application designed to provide convenient one touch access to current weather conditions. It does this by accessing a device's current coarse location via the Fused Location Client. 

The UI is made entirely with Jetpack Compose, and it features a variety of different animations for various weather patterns. A primary goal with most of my applications these days is to only use a library if it's appropriate to. So instead of using Retrofit to make a single HTTP request, I use HttpURLConnection (Java standard library). Instead of using GSON/Moshi, I wrote a simple Recursive Descent Parser using JsonReader (platform class).

### User Interface Examples
**Clear Sky Day**
<img src="day_clear_sky.png" alt="Clear Sky Day" width="270" height="480"/>

**Rain Night**
<img src="night_rain.png" alt="Rain Night" width="270" height="480"/>

**Snow Day**
<img src="day_snowfall.png" alt="Snow Day" width="270" height="480"/>

**Thunder Night**
<img src="night_thunder.png" alt="Thunder Night" width="270" height="480"/>

### Animations
[Clear Sky](/app/src/main/java/com/bracketcove/wiseweather/ui/animations/ClearSky.kt) - Animations for Sun and Stars on Clear days or night