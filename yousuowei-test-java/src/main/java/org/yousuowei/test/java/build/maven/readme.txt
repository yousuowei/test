maven2使用详解

maven2 起步 
      相信maven1 大家都已经很熟悉了，具体maven能做什么，就不详细说了。个人觉得maven在开源项目中用的还是比较多的，公司内部，就不太清楚了。我以前的公司用过一段时间，不过后来就没有下文了。 
     与maven1 相比，maven2可算是几乎重写了，不过从速度来说应该更快。 
     主要的几个新特性包括：（详细参考http://www.ibm.com/developerworks/cn/opensource/os-maven2/index.html） 
    1． 更快、更简单 
        速度方面可以比上ant了 
    2． 更少的配置文件 
        现在的配置文件只剩下了settings.xml和pom.xml了。 
    3.  Plugin语言更换 
        语言开始支持java,BeanShell和ant 
    4.  提供了预定义的模版 
        这点是最有帮助的，用户可以自己定义自己的项目模版了，就像用appfuse一样生成项目结构 
    5． 生命周期的引入 
         在Maven2中有了明确的生命周期概念，而且都提供与之对应的命令，使得项目构建更加清晰明了。 
    6.  新增Dependency Scope    
         这点也比较重要，有些用于test范围的包，可以不用加入依赖了 
    7.  传递依赖，简化依赖管理 
      这是最为方便的，可以省了很多配置。如a 依赖 b,b 依赖c  默认 a也会依赖 c。但是也会带来隐患，如版本冲突。不过maven 也已经考虑到了，可以使用exclusions来排除相应的重复依赖。

      介绍了那么多，现在切入正题，开始maven2 之旅： 
      首些下载需要的工具： 
            maven2: http://maven.apache.org/download.html 最主要的 
            maven-proxy：用来代理repository，使用本地库代替maven2的远程库 
            http://maven-proxy.codehaus.org/ 
            continuum：一个不错的持续整合工具，用于自动build。支持ant,maven 
            http://maven.apache.org/continuum/ 
            svn:版本控制工具相信都已经配置了。 
            maven 用于eclipse的插件 ,在maven主站有下载，不错的插件。当然idea也有相应的插件 
            最后，http，服务器是必不可少的。用于内部开发使用。 
            可以使用apache ，或者jetty  http://www.mortbay.org/

 

maven2安装： 
      安装maven2很简单，把下载来的maven包解开就行了。（目前我的配置都在win2003上，还没有应用于linux，所有所有的配置都针对 windows).增加相应的环境变量m2_home=maven2的安装目录，不要忘了设置java_home的目录。另外在path中增加% m2_home%\bin;可以直接在命令行下面使用mvn。 
其他工具的安装在后续的文章会介绍。

      开始第一个mvean2项目： 
      mvn archetype:create -DgroupId=com.mycompany.app \  -DartifactId=my-app 
      简单介绍一下 groupId相当于你的组织，如同org.springframework，会转化为相应得本地路径 artifactId，你主要的jar包名称，也就是你要打成的jar 名称。 
      编译应用资源 
      mvn compile 
      编译相应的jave 文件 
编译测试类以及运行测试类 
      mvn test 
      运行测试类 
如果只想编译test，执行 
      mvn test-compile 
打包和安装你的本地库 
      打包： mvn package 
      安装： mvn install 
创建web site ：mvn site 
清除所有输出 ： mvn clean 
创建相关的ide文件 mvn idea:idea  或者 mvn eclipse:eclipse

      顺便说一下，maven2 是有生命周期这一概念的，也就是说如果你执行package，相应的以前步骤，如compile,test等都会自动执行。 
刚开始执行会比较慢，需要从maven2远程库中下载所有的文件到本地。如果你的本地没有相应的依赖包，则每次maven都会去远程下载，所以配置一个镜像库就比较重要了。

--------------------------------------------------------------------------------

 

maven 配置篇之settings.xml 
      maven2 配置文件主要集中在pom.xml和settings.xml中。 
      先来说说settings.xml，settings.xml对于maven来说相当于全局性的配置，用于所有的项目。在maven2中存在两个settings.xml，一个位于maven2的安装目录conf下面，作为全局性配置。对于团队设置，保持一致的定义是关键，所以maven2/conf下面的settings.xml就作为团队共同的配置文件。保证所有的团队成员都拥有相同的配置。当然对于每个成员，都需要特殊的 自定义设置，如用户信息，所以另外一个settings.xml就作为本地配置。默认的位置为：${user.dir} /.m2/settings.xml目录中（${user.dir} 指windows 中的用户目录）。 
    settings.xml基本结构如下： 
    <settings xmlns="http://maven.apache.org/POM/4.0.0" 
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
          xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
                              http://maven.apache.org/xsd/settings-1.0.0.xsd"> 
      <localRepository/> 
      <interactiveMode/> 
      <usePluginRegistry/> 
      <offline/> 
      <pluginGroups/> 
      <servers/> 
      <mirrors/> 
      <proxies/> 
      <profiles/> 
      <activeProfiles/> 
    </settings> 
简单介绍一下几个主要的配置因素： 
localRepository：表示本地库的保存位置，也就是maven2主要的jar保存位置，默认在${user.dir}/.m2/repository，如果需要另外设置，就换成其他的路径。 
offline：如果不想每次编译，都去查找远程中心库，那就设置为true。当然前提是你已经下载了必须的依赖包。 
Servers ：在POM中的 distributionManagement元素定义了开发库。然而，特定的username和pwd不能使用于pom.xml，所以通过此配置来保存server信息 
  <servers> 
    <server> 
      <id>server001</id> 
      <username>my_login</username> 
      <password>my_password</password> 
      <privateKey>${usr.home}/.ssh/id_dsa</privateKey> 
      <passphrase>some_passphrase</passphrase> 
      <filePermissions>664</filePermissions> 
      <directoryPermissions>775</directoryPermissions> 
      <configuration></configuration> 
    </server> 
  </servers> 
  id:server 的id,用于匹配distributionManagement库id，比较重要。 
  username, password:用于登陆此服务器的用户名和密码 
  privateKey, passphrase：设置private key，以及passphrase 
  filePermissions, directoryPermissions：当库文件或者目录创建后，需要使用权限进行访问。参照unix文件许可，如664和775 
Mirrors ：表示镜像库，指定库的镜像，用于增加其他库 
  <mirrors> 
    <mirror> 
      <id>planetmirror.com</id> 
      <name>PlanetMirror Australia</name> 
      <url>http://downloads.planetmirror.com/pub/maven2</url> 
      <mirrorOf>central</mirrorOf> 
    </mirror> 
  </mirrors> 
id,name:唯一的标志，用于区别镜像 
url:镜像的url 
mirrorOf：此镜像指向的服务id 
Proxies ：主要用于无法直接访问中心的库用户配置。 
  <proxies> 
    <proxy> 
      <id>myproxy</id> 
      <active>true</active> 
      <protocol>http</protocol> 
      <host>proxy.somewhere.com</host> 
      <port>8080</port> 
      <username>proxyuser</username> 
      <password>somepassword</password> 
      <nonProxyHosts>*.google.com|ibiblio.org</nonProxyHosts> 
    </proxy> 
  </proxies> 
id:代理的标志 
active：是否激活代理 
protocol, host, port:protocol://host:port 代理 
username, password：用户名和密码 
nonProxyHosts: 不需要代理的host 
Profiles ：类似于pom.xml中的profile元素，主要包括activation,repositories,pluginRepositories 和properties元素刚开始接触的时候，可能会比较迷惑，其实这是maven2中比较强大的功能。从字面上来说，就是个性配置。 单独定义profile后，并不会生效，需要通过满足条件来激活。 
repositories 和pluginRepositories 
定义其他开发库和插件开发库。对于团队来说，肯定有自己的开发库。可以通过此配置来定义。 
如下的配置，定义了本地开发库，用于release 发布。 
    <repositories> 
       <repository> 
          <id>repo-local</id> 
          <name>Internal 开发库</name> 
          <url>http://192.168.0.2:8082/repo-local</url> 
          <releases> 
            <enabled>true</enabled> 
            <updatePolicy>never</updatePolicy> 
            <checksumPolicy>warn</checksumPolicy> 
          </releases> 
          <snapshots> 
            <enabled>false</enabled> 
          </snapshots> 
          <layout>default</layout> 
       </repository> 
    </repositories> 
    <pluginRepositories> 
      <pluginRepository> 
        <id>repo-local</id> 
        <name>Internal 开发库</name> 
        <url>http://192.168.0.2:8082/repo-local</url> 
        <releases> 
            <enabled>true</enabled> 
            <updatePolicy>never</updatePolicy> 
            <checksumPolicy>warn</checksumPolicy> 
        </releases> 
        <snapshots> 
          <enabled>false</enabled> 
        </snapshots> 
        <layout>default</layout> 
      </pluginRepository> 
    </pluginRepositories> 
releases, snapshots:每个产品的版本的Release或者snapshot(注：release和snapshot的区别，release一般是比较稳定的版本，而snapshot基本上不稳定，只是作为快照）

properties ：maven 的properties作为placeholder值，如ant的properties。 
包括以下的5种类型值： 
1. env.X，返回当前的环境变量 
2. project.x:返回pom中定义的元素值，如project.version 
3. settings.x：返回settings.xml中定义的元素 
4. java 系统属性：所有经过java.lang.System.getProperties()返回的值 
5. x：用户自己设定的值 
Activation ：用于激活此profile 
  <activation> 
        <activeByDefault>false</activeByDefault> 
        <jdk>1.5</jdk> 
        <os> 
          <name>Windows XP</name> 
          <family>Windows</family> 
          <arch>x86</arch> 
          <version>5.1.2600</version> 
        </os> 
        <property> 
          <name>mavenVersion</name> 
          <value>2.0.3</value> 
        </property> 
        <file> 
          <exists>${basedir}/file2.properties</exists> 
          <missing>${basedir}/file1.properties</missing> 
        </file> 
  </activation> 
jdk:如果匹配指定的jdk版本，将会激活 
os:操作系统 
property：如果maven能检测到相应的属性 
file: 用于判断文件是否存在或者不存在

除了使用activation来激活profile，同样可以通过activeProfiles来激活 
ActiveProfiles 
表示激活的profile,通过profile id来指定。 
  <activeProfiles> 
    <activeProfile>env-test</activeProfile> 指定的profile id 
  </activeProfiles>


--------------------------------------------------------------------------------

 

maven 配置篇 之pom.xml 

     pom作为项目对象模型。通过xml表示maven项目，使用pom.xml来实现。主要描述了项目：包括配置文件；开发者需要遵循的规则，缺陷管理系统，组织和licenses，项目的url，项目的依赖性，以及其他所有的项目相关因素。 
快速察看： 
<project> 
  <modelVersion>4.0.0</modelVersion>

  <!-- The Basics --> 
  <groupId>...</groupId> 
  <artifactId>...</artifactId> 
  <version>...</version> 
  <packaging>...</packaging> 
  <dependencies>...</dependencies> 
  <parent>...</parent> 
  <dependencyManagement>...</dependencyManagement> 
  <modules>...</modules> 
  <properties>...</properties>

  <!-- Build Settings --> 
  <build>...</build> 
  <reporting>...</reporting>

  <!-- More Project Information --> 
  <name>...</name> 
  <description>...</description> 
  <url>...</url> 
  <inceptionYear>...</inceptionYear> 
  <licenses>...</licenses> 
  <organization>...</organization> 
  <developers>...</developers> 
  <contributors>...</contributors>

  <!-- Environment Settings --> 
  <issueManagement>...</issueManagement> 
  <ciManagement>...</ciManagement> 
  <mailingLists>...</mailingLists> 
  <scm>...</scm> 
  <prerequisites>...</prerequisites> 
  <repositories>...</repositories> 
  <pluginRepositories>...</pluginRepositories> 
  <distributionManagement>...</distributionManagement> 
  <profiles>...</profiles> 
</project>

基本内容： 
     POM包括了所有的项目信息。 
maven 相关： pom定义了最小的maven2元素，允许groupId,artifactId,version。所有需要的元素 
      groupId:项目或者组织的唯一标志，并且配置时生成的路径也是由此生成，如org.codehaus.mojo生成的相对路径为：/org/codehaus/mojo 
      artifactId: 项目的通用名称 
      version:项目的版本 
      packaging: 打包的机制，如pom, jar, maven-plugin, ejb, war, ear, rar, par 
      classifier: 分类 
POM关系： 
     主要为依赖，继承，合成 
     依赖关系： 
  <dependencies> 
    <dependency> 
      <groupId>junit</groupId> 
      <artifactId>junit</artifactId> 
      <version>4.0</version> 
      <type>jar</type> 
      <scope>test</scope> 
      <optional>true</optional> 
    </dependency> 
    ... 
  </dependencies> 
groupId, artifactId, version:描述了依赖的项目唯一标志 
可以通过以下方式进行安装： 
·   使用以下的命令安装： 
·   mvn install:install-file –Dfile=non-maven-proj.jar –DgroupId=some.group –DartifactId=non-maven-proj –Dversion=1 
·   创建自己的库,并配置，使用deploy:deploy-file 
·   设置此依赖范围为system，定义一个系统路径。不提倡。 
type:相应的依赖产品包形式，如jar，war 
scope:用于限制相应的依赖范围，包括以下的几种变量： 
·   compile ：默认范围，用于编译 
·   provided：类似于编译，但支持你期待jdk或者容器提供，类似于classpath 
·   runtime:在执行时，需要使用 
·   test:用于test任务时使用 
·   system:需要外在提供相应得元素。通过systemPath来取得 
systemPath: 仅用于范围为system。提供相应的路径 
optional: 标注可选，当项目自身也是依赖时。用于连续依赖时使用 
   独占性    
   外在告诉maven你只包括指定的项目，不包括相关的依赖。此因素主要用于解决版本冲突问题 
  <dependencies> 
    <dependency> 
      <groupId>org.apache.maven</groupId> 
      <artifactId>maven-embedder</artifactId> 
      <version>2.0</version> 
      <exclusions> 
        <exclusion> 
          <groupId>org.apache.maven</groupId> 
          <artifactId>maven-core</artifactId> 
        </exclusion> 
      </exclusions> 
    </dependency> 
表示项目maven-embedder需要项目maven-core，但我们不想引用maven-core

      继承关系 
      另一个强大的变化,maven带来的是项目继承。主要的设置： 
定义父项目 
<project> 
  <modelVersion>4.0.0</modelVersion> 
  <groupId>org.codehaus.mojo</groupId> 
  <artifactId>my-parent</artifactId> 
  <version>2.0</version> 
  <packaging>pom</packaging> 
</project> 
    packaging 类型，需要pom用于parent和合成多个项目。我们需要增加相应的值给父pom，用于子项目继承。主要的元素如下：
   依赖型 
开发者和合作者 
插件列表 
报表列表 
插件执行使用相应的匹配ids 
插件配置 
子项目配置 
<project> 
  <modelVersion>4.0.0</modelVersion> 
  <parent> 
    <groupId>org.codehaus.mojo</groupId> 
    <artifactId>my-parent</artifactId> 
    <version>2.0</version> 
    <relativePath>../my-parent</relativePath> 
  </parent> 
  <artifactId>my-project</artifactId> 
</project> 
relativePath可以不需要，但是用于指明parent的目录，用于快速查询。

dependencyManagement： 
用于父项目配置共同的依赖关系，主要配置依赖包相同因素，如版本，scope。

合成（或者多个模块） 
    一个项目有多个模块，也叫做多重模块，或者合成项目。 
如下的定义： 
<project> 
  <modelVersion>4.0.0</modelVersion> 
  <groupId>org.codehaus.mojo</groupId> 
  <artifactId>my-parent</artifactId> 
  <version>2.0</version> 
  <modules> 
    <module>my-project1<module> 
    <module>my-project2<module> 
  </modules> 
</project>

build 设置 
    主要用于编译设置，包括两个主要的元素，build和report 
    build 
    主要分为两部分，基本元素和扩展元素集合 
注意：包括项目build和profile build 
<project> 
  <!-- "Project Build" contains more elements than just the BaseBuild set --> 
  <build>...</build> 
  <profiles> 
    <profile> 
      <!-- "Profile Build" contains a subset of "Project Build"s elements --> 
      <build>...</build> 
    </profile> 
  </profiles> 
</project>

基本元素 
<build> 
  <defaultGoal>install</defaultGoal> 
  <directory>${basedir}/target</directory> 
  <finalName>${artifactId}-${version}</finalName> 
  <filters> 
    <filter>filters/filter1.properties</filter> 
  </filters> 
  ... 
</build> 
defaultGoal: 定义默认的目标或者阶段。如install 
directory: 编译输出的目录 
finalName: 生成最后的文件的样式 
filter: 定义过滤，用于替换相应的属性文件，使用maven定义的属性。设置所有placehold的值

资源(resources) 
    你项目中需要指定的资源。如spring配置文件,log4j.properties 
<project> 
  <build> 
    ... 
    <resources> 
      <resource> 
        <targetPath>META-INF/plexus</targetPath> 
        <filtering>false</filtering> 
        <directory>${basedir}/src/main/plexus</directory> 
        <includes> 
          <include>configuration.xml</include> 
        </includes> 
        <excludes> 
          <exclude>**/*.properties</exclude> 
        </excludes> 
      </resource> 
    </resources> 
    <testResources> 
      ... 
    </testResources> 
    ... 
  </build> 
</project> 
resources: resource的列表，用于包括所有的资源 
targetPath: 指定目标路径，用于放置资源，用于build 
filtering: 是否替换资源中的属性placehold 
directory: 资源所在的位置 
includes: 样式，包括那些资源 
excludes: 排除的资源 
testResources: 测试资源列表 
插件 
  在build时，执行的插件，比较有用的部分，如使用jdk 5.0编译等等 
<project> 
  <build> 
    ... 
    <plugins> 
      <plugin> 
        <groupId>org.apache.maven.plugins</groupId> 
        <artifactId>maven-jar-plugin</artifactId> 
        <version>2.0</version> 
        <extensions>false</extensions> 
        <inherited>true</inherited> 
        <configuration> 
          <classifier>test</classifier> 
        </configuration> 
        <dependencies>...</dependencies> 
        <executions>...</executions> 
      </plugin> 
    </plugins> 
  </build> 
</project> 
extensions: true or false，是否装载插件扩展。默认false 
inherited: true or false，是否此插件配置将会应用于poms，那些继承于此的项目 
configuration: 指定插件配置 
dependencies: 插件需要依赖的包 
executions: 用于配置execution目标，一个插件可以有多个目标。 
如下： 
    <plugin> 
        <artifactId>maven-antrun-plugin</artifactId>

        <executions> 
          <execution> 
            <id>echodir</id> 
            <goals> 
              <goal>run</goal> 
            </goals> 
            <phase>verify</phase> 
            <inherited>false</inherited> 
            <configuration> 
              <tasks> 
                <echo>Build Dir: ${project.build.directory}</echo> 
              </tasks> 
            </configuration> 
          </execution> 
        </executions> 
      </plugin> 
  说明： 
id:规定execution 的唯一标志 
goals: 表示目标 
phase: 表示阶段，目标将会在什么阶段执行 
inherited: 和上面的元素一样，设置false maven将会拒绝执行继承给子插件 
configuration: 表示此执行的配置属性

插件管理 
    pluginManagement：插件管理以同样的方式包括插件元素，用于在特定的项目中配置。所有继承于此项目的子项目都能使用。主要定义插件的共同元素

扩展元素集合 
主要包括以下的元素： 
Directories 
用于设置各种目录结构，如下： 
  <build> 
    <sourceDirectory>${basedir}/src/main/java</sourceDirectory> 
    <scriptSourceDirectory>${basedir}/src/main/scripts</scriptSourceDirectory> 
    <testSourceDirectory>${basedir}/src/test/java</testSourceDirectory> 
    <outputDirectory>${basedir}/target/classes</outputDirectory> 
    <testOutputDirectory>${basedir}/target/test-classes</testOutputDirectory> 
    ... 
  </build>

Extensions

表示需要扩展的插件，必须包括进相应的build路径。

<project> 
  <build> 
    ... 
    <extensions> 
      <extension> 
        <groupId>org.apache.maven.wagon</groupId> 
        <artifactId>wagon-ftp</artifactId> 
        <version>1.0-alpha-3</version> 
      </extension> 
    </extensions> 
    ... 
  </build> 
</project>

Reporting 
    用于在site阶段输出报表。特定的maven 插件能输出相应的定制和配置报表。 
  <reporting> 
    <plugins> 
      <plugin> 
        <outputDirectory>${basedir}/target/site</outputDirectory> 
        <artifactId>maven-project-info-reports-plugin</artifactId> 
        <reportSets> 
          <reportSet></reportSet> 
        </reportSets> 
      </plugin> 
    </plugins> 
  </reporting>

Report Sets 
    用于配置不同的目标，应用于不同的报表 
<reporting> 
    <plugins> 
      <plugin> 
        ... 
        <reportSets> 
          <reportSet> 
            <id>sunlink</id> 
            <reports> 
              <report>javadoc</report> 
            </reports> 
            <inherited>true</inherited> 
            <configuration> 
              <links> 
                <link>http://java.sun.com/j2se/1.5.0/docs/api/</link> 
              </links> 
            </configuration> 
          </reportSet> 
        </reportSets> 
      </plugin> 
    </plugins> 
</reporting>

更多的项目信息 
name:项目除了artifactId外，可以定义多个名称 
description: 项目描述 
url: 项目url 
inceptionYear:创始年份

Licenses 
<licenses> 
  <license> 
    <name>Apache 2</name> 
    <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url> 
    <distribution>repo</distribution> 
    <comments>A business-friendly OSS license</comments> 
  </license> 
</licenses>

Organization 
配置组织信息 
  <organization> 
    <name>Codehaus Mojo</name> 
    <url>http://mojo.codehaus.org</url> 
  </organization>

Developers 
配置开发者信息 
<developers> 
    <developer> 
      <id>eric</id> 
      <name>Eric</name> 
      <email>eredmond@codehaus.org</email> 
      <url>http://eric.propellors.net</url> 
      <organization>Codehaus</organization> 
      <organizationUrl>http://mojo.codehaus.org</organizationUrl> 
      <roles> 
        <role>architect</role> 
        <role>developer</role> 
      </roles> 
      <timezone>-6</timezone> 
      <properties> 
        <picUrl>http://tinyurl.com/prv4t</picUrl> 
      </properties> 
    </developer> 
  </developers>

Contributors 
  <contributors> 
    <contributor> 
      <name>Noelle</name> 
      <email>some.name@gmail.com</email> 
      <url>http://noellemarie.com</url> 
      <organization>Noelle Marie</organization> 
      <organizationUrl>http://noellemarie.com</organizationUrl> 
      <roles> 
        <role>tester</role> 
      </roles> 
      <timezone>-5</timezone> 
      <properties> 
        <gtalk>some.name@gmail.com</gtalk> 
      </properties> 
    </contributor> 
  </contributors>

环境设置

Issue Management 
    定义相关的bug跟踪系统，如bugzilla,testtrack,clearQuest等 
  <issueManagement> 
    <system>Bugzilla</system> 
    <url>http://127.0.0.1/bugzilla</url> 
  </issueManagement> 
Continuous Integration Management 
连续整合管理，基于triggers或者timings 
  <ciManagement> 
    <system>continuum</system> 
    <url>http://127.0.0.1:8080/continuum</url> 
    <notifiers> 
      <notifier> 
        <type>mail</type> 
        <sendOnError>true</sendOnError> 
        <sendOnFailure>true</sendOnFailure> 
        <sendOnSuccess>false</sendOnSuccess> 
        <sendOnWarning>false</sendOnWarning> 
        <configuration><address>continuum@127.0.0.1</address></configuration> 
      </notifier> 
    </notifiers> 
  </ciManagement>

Mailing Lists 
  <mailingLists> 
    <mailingList> 
      <name>User List</name> 
      <subscribe>user-subscribe@127.0.0.1</subscribe> 
      <unsubscribe>user-unsubscribe@127.0.0.1</unsubscribe> 
      <post>user@127.0.0.1</post> 
      <archive>http://127.0.0.1/user/</archive> 
      <otherArchives> 
        <otherArchive>http://base.google.com/base/1/127.0.0.1</otherArchive> 
      </otherArchives> 
    </mailingList> 
  </mailingLists>

SCM 
  软件配置管理，如cvs 和svn 
  <scm> 
    <connection>scm:svn:http://127.0.0.1/svn/my-project</connection> 
    <developerConnection>scm:svn:https://127.0.0.1/svn/my-project</developerConnection> 
    <tag>HEAD</tag> 
    <url>http://127.0.0.1/websvn/my-project</url> 
  </scm>

Repositories

配置同setting.xml中的开发库

Plugin Repositories 
配置同 repositories

Distribution Management 
用于配置分发管理，配置相应的产品发布信息,主要用于发布，在执行mvn deploy后表示要发布的位置 
1 配置到文件系统 
<distributionManagement> 
<repository> 
<id>proficio-repository</id> 
<name>Proficio Repository</name> 
<url>file://${basedir}/target/deploy</url> 
</repository> 
</distributionManagement> 
2 使用ssh2配置 
<distributionManagement> 
<repository> 
<id>proficio-repository</id> 
<name>Proficio Repository</name> 
<url>scp://sshserver.yourcompany.com/deploy</url> 
</repository> 
</distributionManagement> 
3 使用sftp配置 
<distributionManagement> 
<repository> 
<id>proficio-repository</id> 
<name>Proficio Repository</name> 
<url>sftp://ftpserver.yourcompany.com/deploy</url> 
</repository> 
</distributionManagement> 
4 使用外在的ssh配置 
    编译扩展用于指定使用wagon外在ssh提供，用于提供你的文件到相应的远程服务器。 
<distributionManagement> 
<repository> 
<id>proficio-repository</id> 
<name>Proficio Repository</name> 
<url>scpexe://sshserver.yourcompany.com/deploy</url> 
</repository> 
</distributionManagement> 
<build> 
<extensions> 
<extension> 
<groupId>org.apache.maven.wagon</groupId> 
<artifactId>wagon-ssh-external</artifactId> 
<version>1.0-alpha-6</version> 
</extension> 
</extensions> 
</build>

5 使用ftp配置 
<distributionManagement> 
<repository> 
<id>proficio-repository</id> 
<name>Proficio Repository</name> 
<url>ftp://ftpserver.yourcompany.com/deploy</url> 
</repository> 
</distributionManagement> 
<build> 
<extensions> 
<extension> 
<groupId>org.apache.maven.wagon</groupId> 
<artifactId>wagon-ftp</artifactId> 
<version>1.0-alpha-6</version> 
</extension> 
</extensions> 
</build>

repository 对应于你的开发库，用户信息通过settings.xml中的server取得

Profiles 
类似于settings.xml中的profiles，增加了几个元素，如下的样式： 
  <profiles> 
    <profile> 
      <id>test</id> 
      <activation>...</activation> 
      <build>...</build> 
      <modules>...</modules> 
      <repositories>...</repositories> 
      <pluginRepositories>...</pluginRepositories> 
      <dependencies>...</dependencies> 
      <reporting>...</reporting> 
      <dependencyManagement>...</dependencyManagement> 
      <distributionManagement>...</distributionManagement> 
    </profile> 
  </profiles>


--------------------------------------------------------------------------------

 

使用maven2 进行团队配置 
    对于团队来说，建立统一的开发环境是必须的，而maven能很好帮助建立统一的环境。下面就介绍如何更有效的进行统一的配置。 
准备工作： 
   下载必须的软件： 
maven2: http://maven.apache.org/download.html 最主要的 
maven-proxy：用来代理repository，使用代理来访问多个远程库 
            http://maven-proxy.codehaus.org/ 
continuum：一个不错的持续整合工具，用于自动build。支持ant,maven 
http://maven.apache.org/continuum/ 
svn:版本控制工具

创建一致的开发环境 
   
    在共享的开发环境中，更好的建议是保持maven的两个不同的配置文件分别管理，包括共享和用户自定义设置。共同的配置包括在安装目录中，而单独的开发设置保存在用户本地目录。 
    
    全局的配置文件settings.xml

<servers> 
       //公司内部库，所有的release版本,serverid对应于repository id，用于在deploy时，访问使用，主要保存用户名和密码 
<server> 
<id>internal</id> 
<username>${website.username}</username> 
<password>${website.pwd}</password> 
<filePermissions>664</filePermissions> 
<directoryPermissions>775</directoryPermissions> 
</server> 
//目前的开发库，用于snapshot库 
<server> 
<id>snapshot</id> 
<username>${website.username}</username> 
<password>${website.pwd}</password> 
<filePermissions>664</filePermissions> 
<directoryPermissions>775</directoryPermissions> 
</server> 
</servers>

<profiles> 
<!--定义核心库 maven 镜像,由maven-proxy实现--> 
<profile> 
<id>central-repo</id> 
<repositories> 
<repository> 
<id>central</id> 
<name>Internal Repository</name> 
<url>http://192.168.0.2:9999/repository</url> 
</repository> 
</repositories> 
<pluginRepositories> 
<pluginRepository> 
<id>central</id> 
<name>Internal Repository</name> 
<url>http://192.168.0.2:9999/repository</url> 
</pluginRepository> 
</pluginRepositories> 
</profile>

<!--定义内部库，包括公司的所有release版本--> 
<profile> 
<id>internal-repo</id> 
<repositories> 
<repository> 
<id>internal</id> 
<name>Internal Repository</name> 
<url>http://192.168.0.2:8080/repo-local</url> 
<releases> 
<enabled>true</enabled> 
<updatePolicy>never</updatePolicy> 
<checksumPolicy>warn</checksumPolicy> 
</releases> 
</repository> 
</repositories> 
<pluginRepositories> 
<pluginRepository> 
<id>internal</id> 
<name>Internal Plugin Repository</name> 
<url>http://192.168.0.2:8080/repo-local</url> 
<releases> 
<enabled>true</enabled> 
<updatePolicy>never</updatePolicy> 
<checksumPolicy>warn</checksumPolicy> 
</releases> 
</pluginRepository> 
</pluginRepositories> 
</profile> 
<!--定义内部开发库 ，也可以合并snapshot和release--> 
<profile> 
<id>snapshot-repo</id> 
<repositories> 
<repository> 
<id>snapshot</id> 
<name>Internal Repository</name> 
<url>http://192.168.0.2:8080/repo-snapshot</url> 
<snapshots> 
<enabled>true</enabled> 
<updatePolicy>interval:60</updatePolicy> 
<checksumPolicy>warn</checksumPolicy> 
</snapshots> 
</repository> 
</repositories> 
<pluginRepositories> 
<pluginRepository> 
<id>snapshot</id> 
<name>Internal Plugin Repository</name> 
<url>http://192.168.0.2:8080/repo-snapshot</url> 
<snapshots> 
<enabled>true</enabled> 
<updatePolicy>interval:60</updatePolicy> 
<checksumPolicy>warn</checksumPolicy> 
</snapshots> 
</pluginRepository> 
</pluginRepositories> 
</profile> 
</profiles> 
<!-- 激活相应得配置--> 
<activeProfiles> 
<activeProfile>central-repo</activeProfile> 
<activeProfile>internal-repo</activeProfile> 
<activeProfile>snapshot-repo</activeProfile> 
</activeProfiles> 
<!-- 插件默认groupId --> 
<pluginGroups> 
<pluginGroup>com.mycompany.plugins</pluginGroup> 
</pluginGroups>

包括了以下的共享因素： 
服务器设置典型是共同的，只有用户名需要在用户环境中设置。使用一致的定义来配置共同的设置 
profile定义了共同的因素，内部开发库，包括指定的组织或者部门发布的产品。这些库独立于核心开发库。 
激活的profiles列表，用于激活相应的profile 
plugin 组只有当你的组织中有自己定义的插件，用于命令行运行在pom中定义。 
对于单独的用户来说，设置如下：

<settings> 
<profiles> 
<profile> 
<id>property-overrides</id> 
<properties> 
<website.username>myuser</website.username> 
<website.pwd>test</website.username> 
</properties> 
</profile> 
</profiles> 
</settings>


创建共享开发库 
    大多数组织将会创建自己的内部开发库，用于配置，而中心开发库用于连接maven 
    设置内部开发库是简单的，使用http协议，可以使用存在的http 服务器。或者创建新的服务，使用apache，或者jetty 
    假设服务器地址192.168.0.2 ,端口8080 
    http://192.168.0.2:8080/repo-local 
    设置另外一个开发库，用于设置项目的snapshot库http://192.168.0.2:8080/repo-snapshot 
    中心镜像库，使用maven-proxy创建，当然也可以创建自己的镜像。用于下载本地库中没有的artifact


maven-proxy 设置 
    从网上直接下载maven-proxy-standalone-0.2-app.jar和 proxy.properties 
    在命令行中，直接运行java -jar maven-proxy-standalone-0.2-app.jar  proxy.properties 
主要的配置： 
设置repo.list 中增加相应的库就可以，如下定义： 
repo.list=repo1.maven.org,... 
#maven 的中心库 
repo.repo1.maven.org.url=http://repo1.maven.org/maven2 
repo.repo1.maven.org.description=maven.org 
repo.repo1.maven.org.proxy=one 
repo.repo1.maven.org.hardfail=false 
repo.repo1.maven.org.cache.period=360000 
repo.repo1.maven.org.cache.failures=true 
以后所有的远程库，都通过此方式增加。顺便说一下，不要忘了注释原来的example，那是没有办法访问的。

其他配置如 
端口号 port=9999 
保存的位置 repo.local.store=target/repo 
serverName=http://localhost:9999

创建标准的组织pom 
定义共同的内容，包括公司的结构，如组织，部门以及团队。 
察看一下maven 的自身，可以作为很好的参考。 
如scm 
 


<project> 
<modelVersion>4.0.0</modelVersion> 
<parent> 
<groupId>org.apache.maven</groupId> 
<artifactId>maven-parent</artifactId> 
<version>1</version> 
</parent> 
<groupId>org.apache.maven.scm</groupId> 
<artifactId>maven-scm</artifactId> 
<url>http://maven.apache.org/maven-scm/</url> 
... 
<modules> 
<module>maven-scm-api</module> 
<module>maven-scm-providers</module> 
... 
</modules> 
</project>    


在maven父项目中可以看到如下定义：

 

<project> 
<modelVersion>4.0.0</modelVersion> 
<parent> 
<groupId>org.apache</groupId> 
<artifactId>apache</artifactId> 
<version>1</version> 
</parent> 
<groupId>org.apache.maven</groupId> 
<artifactId>maven-parent</artifactId> 
<version>5</version> 
<url>http://maven.apache.org/</url> 
... 
<mailingLists> 
<mailingList> 
<name>Maven Announcements List</name> 
<post>announce@maven.apache.org</post> 
... 
</mailingList> 
</mailingLists> 
<developers> 
<developer> 
... 
</developer> 
</developers> 
</project>    

maven 父pom包括了共享的元素，如声明邮件列表，开发者。并且大多数项目继承apache组织：

<project> 
<modelVersion>4.0.0</modelVersion> 
<groupId>org.apache</groupId> 
<artifactId>apache</artifactId> 
<version>1</version> 
<organization> 
<name>Apache Software Foundation</name> 
<url>http://www.apache.org/</url> 
</organization> 
<url>http://www.apache.org/</url> 
... 
<repositories> 
<repository> 
<id>apache.snapshots</id> 
<name>Apache Snapshot Repository</name> 
<url>http://svn.apache.org/maven-snapshot-repository</url> 
<releases> 
<enabled>false</enabled> 
</releases> 
</repository> 
</repositories> 
... 
<distributionManagement> 
<repository> 
... 
</repository> 
<snapshotRepository> 
... 
</snapshotRepository> 
</distributionManagement> 
</project>    

 

对于项目自身来说，父pom很少更新。所以，最后的方式保存父pom文件在单独的版本控制区域，它们能够check out，更改和配置.

使用Continuum持久整合

    持续整合自动build你的项目，通过一定的时间，包括所有的冲突在早期察觉，而不是发布的时候。另外持续整合也是一种很好的开发方式，使团队成员能产生细微的，交互的变动，能更有效的支持平行开发进程。 
    可以使用maven的continuum作为持久整合的服务。 
    安装continuum，比较简，使用以下的命令： 
    C:\mvnbook\continuum-1.0.3> bin\win32\run 
    可以通过http://localhost:8082/continuum来验证 
    为了支持continuum 发送e-mail提醒，你需要相应的smtp服务用于发送信息。默认使用localhost:25，如果你没有设置，编辑上面的文件改变smtp-host设置。 
    下一步，设置svn目录： 
    svn co http://www.cnblogs.com/flyingzqx/admin/file:///C:/mvnbook/svn/proficio/trunk proficio 
    编辑pom.xml用于正确相应得e-mail地址。

 

... 
<ciManagement> 
<system>continuum</system> 
<url>http://localhost:8080/continuum 
<notifiers> 
<notifier> 
<type>mail</type> 
<configuration> 
<address>youremail@yourdomain.com</address> 
</configuration> 
</notifier> 
</notifiers> 
</ciManagement> 
... 
<scm> 
<connection> 
scm:svn:file://localhost/c:/mvnbook/svn/proficio/trunk 
</connection> 
<developerConnection> 
scm:svn:file://localhost/c:/mvnbook/svn/proficio/trunk 
</developerConnection> 
</scm> 
... 
<distributionManagement> 
<site> 
<id>website</id> 
<url> 
http://www.cnblogs.com/flyingzqx/admin/file:///c:/mvnbook/repository/sites/proficio 
/reference/${project.version} 
</url> 
</site> 
</distributionManagement>   

 

提交相应的pom,然后执行mvn install

如果你返回http://localhost:8082/continuum，你会看到相应的项目列表。

一旦你登录后，你可以选择mavan 2.0项目用于增加相应的项目。你可以增加你的url或者提交你的本地内容。

你可以使用本地pom url，如下http://www.cnblogs.com/flyingzqx/admin/file:///c:mvnbook/proficio/pom.xml

在提交了此url后，continuum将会返回相应的成功信息。 
以下的原则用于更好的帮助持续整合： 
早提交，经常提交：当用户经常提交时，持续整合是最有效的。这并不意味着，提交不正确的代码。 
经常运行build：用于最快检测失败 
尽快修正失败：当失败发生时，应该马上修正失败 
建议一个有效的版本 
运行clean build 
运行复杂的综合测试 
build所有的项目结构分支 
持续运行项目的拷