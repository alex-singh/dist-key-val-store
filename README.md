# Distributed key-value store

To compile and run the distributed key-value store one needs Maven.

Open 3 terminal windows. Write this in the first window:
```
mvn compile exec:java -Dexec.mainClass="com.distkeyvalstore.DistKeyValStoreApplication" -Dserver.port=8080
```
Then, in the second window:
```
mvn compile exec:java -Dexec.mainClass="com.distkeyvalstore.DistKeyValStoreApplication" -Dserver.port=8081
```
And finally, in the third window:
```
mvn compile exec:java -Dexec.mainClass="com.distkeyvalstore.DistKeyValStoreApplication" -Dserver.port=8082
```

Now you have 3 nodes running in the cluster. The node addresses are defined in the application.properties file.

## Discussion of technology choises and solution
I have chosen java because this is what is mainly used at Delta Projects and also I am pretty familiar with it. 

I have chosen to use spring boot becuase it is a fast way to get microservices running with a small amount of code.

I have chosen to use the dependency injection available in spring (autowiring) because it keeps the amount of code needed down. Dependency injection isn't used consistently in all places. There is no good reason for this.

I have also used springs http framework becuase it is a fast and easy way to create REST-api:s and clients.

The program logic is divided into controller, service and an http-client, according to the typical pattern in spring applications.
The http-client is used to call the other nodes in the cluster to synchronise them.
