## PlasmoLib

В основном создана для того чтобы не делать ctrl+c ctrl+v с проектов Pepega.

Сделано плагином, чтобы можно было установить depend.

## Установка
#### Maven
```xml
<project>
    <repositories>
        <repository>
            <id>plasmo-repo</id>
            <url>https://repo.plo.su</url>
        </repository>
    </repositories>
    
    <dependencies>
        <dependency>
            <groupId>su.plo</groupId>
            <artifactId>lib</artifactId>
            <version>0.0.7</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
</project>
```
#### Gradle
```
dependencies {
    compileOnly 'su.plo:lib:0.0.7'
}

repositories {
    maven { url 'https://repo.plo.su/' }
}
```

## MySQL
#### Инициализация в плагине

```java
import su.plo.lib.mysql.MySQL;

class GreatPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        //...
        saveDefaultConfig();
        // Инициализировать нужно после загрузки конфига
        MySQL.init(this);
        //..
    }
}
```
#### Конфиг для плагина
```yaml
mysql:
  host: '127.0.0.1'
  port: 3306
  db: 'great_plugin'
  user: 'user'
  password: 'password'
```
#### Использование в коде
```java
import su.plo.lib.mysql.MySQL;
import java.sql.SQLException;

class GreatCode {
    private final JavaPlugin plugin;
    
    public GreatCode(JavaPlugin plugin) {
        this.plugin = plugin;

        this.doSomething();
    }

    public void doSomething() {
        try(Connection conn = MySQL.getConnection(this.plugin); // Получаем MySQL подключение
            PreparedStatement st = conn.prepareStatement("SELECT * FROM pepega")) {
            //..
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```
## NATS
#### Инициализация в плагине
```java
import su.plo.lib.nats.NatsLib;

class GreatPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        //...
        saveDefaultConfig();
        // Инициализировать нужно после загрузки конфига
        NatsLib.init(this);
        //..
    }
}
```
#### Конфиг для плагина
```yaml
nats:
  ip: 127.0.0.1
  port: 4222
  secret: pepega
  subject: pepega
```
#### Использование в коде
```java
import com.google.common.io.ByteArrayDataOutput;
import io.nats.client.Dispatcher;
import su.plo.lib.nats.NatsClient;
import su.plo.lib.nats.NatsLib;

class GreatCode {
    private final JavaPlugin plugin;
    private final NatsClient nats;
    
    public GreatCode(JavaPlugin plugin) {
        this.plugin = plugin;
        this.nats = NatsLib.get(plugin); // получаем NATS клиент для удобности

        this.subscribe();
        this.publish();
    }
    
    public void subscribe() {
        Dispatcher dispatcher = this.nats.getConnection().createDispatcher((msg) -> {
           // do something
        });
        
        dispatcher.subscribe(this.nats.getSubject("something"));
    }
    
    public void publish() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Pepega Clap");
        this.nats.publish("something", out.toByteArray());
    }
}
```
