# **airly-console-client**

Can display current air condition (temperature, air quality index, humidity, pollution etc.) based on sensor-id or coordinates and facultatively the history of measurements.

### Arguments restrictions:

First one (or two) arguments have to be either

* sensor-id
* latitude and longitude

then, you can apply none, one or both additional arguments

* api-key
* history

If you don't include api-key in the arguments, you will have to create it as your environmental variable, e.g.:

* export API_KEY
* API_KEY=c6ba11add8c04c9eb084545f62fa49cb

Lastly, you can pass arguments in two ways - with space or with equal sign

* `--sensor-id=2364`
* `--sensor-id 2364`

Few examples below

* `java jar airly-console-client --sensor-id=2364`
* `java jar airly-console-client --sensor-id 2364`
* `java jar airly-console-client --sensor-id=2364 --api-key=c6ba11add8c04c9eb084545f62fa49cb`
* `java jar airly-console-client --sensor-id 2364 --api-key=c6ba11add8c04c9eb084545f62fa49cb`
* `java jar airly-console-client --sensor-id=2364 --api-key c6ba11add8c04c9eb084545f62fa49cb`
* `java jar airly-console-client --sensor-id 2364 --api-key c6ba11add8c04c9eb084545f62fa49cb`
* `java jar airly-console-client --sensor-id=2364 --api-key=c6ba11add8c04c9eb084545f62fa49cb --history`

* `java jar airly-console-client --latitude=50.06 --longitude=19.93`
* `java jar airly-console-client --latitude=50.06 --longitude=19.93 --history`
* `java jar airly-console-client --latitude=50.06 --longitude=19.93 --api-key=c6ba11add8c04c9eb084545f62fa49cb`
* `java jar airly-console-client --latitude=50.06 --longitude=19.93 --api-key=c6ba11add8c04c9eb084545f62fa49cb --history`
* `java jar airly-console-client --longitude=19.93 --latitude=50.06`
* `java jar airly-console-client --longitude=19.93 --latitude=50.06 --history`


