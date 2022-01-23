# Distributed key-value store

To run the distributed key-value store one needs Maven.

Open 3 terminal windows. Write this in the first window:
```
mvn exec:java -Dexec.mainClass="com.distkeyvalstore.DistKeyValStoreApplication" -Dserver.port=8080
```
Then, in the second window:
```
mvn exec:java -Dexec.mainClass="com.distkeyvalstore.DistKeyValStoreApplication" -Dserver.port=8081
```
And finally, in the third window:
```
mvn exec:java -Dexec.mainClass="com.distkeyvalstore.DistKeyValStoreApplication" -Dserver.port=8082
```

Now you have 3 nodes running in the cluster. The node addresses are defined in the application.properties file.
