### **Build:**

```
mvn package
```

### Run:

```
java -Dlogging.level.org.redisson=DEBUG -DredissonConfFile=redisson-session.conf -jar target/redisson-test.jar
```