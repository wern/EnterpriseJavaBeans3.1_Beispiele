=======================================================
  Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
      Das EJB-Praxisbuch fuer Ein- und Umsteiger
          Von Werner Eberling und Jan Lessner
         Hanser Fachbuchverlag Muenchen, 2011
   http://www.hanser.de/buch.asp?isbn=3-446-42259-5
       Feedback an ejb3buch@werner-eberling.de
=======================================================

Das Verzeichnis src enthaelt die Quellcodes fuer die im
Buch besprochenen Beispiele. Die Packages auf oberster
Ebene (hello, jms, transaktionen etc.) bilden jeweils
ein eigenstaendiges EJB-Modul, das auf einem EJB-3.1-
Application-Server deployed werden kann. Jedes Package
enthaelt ein Buildskript, um die Quellen zu uebersetzen,
das Modul zu bauen und auf einem JBoss-Server zu
deployen. Die Ausfuehrung der Buildskripte erfordert das
Build-Werkzeug Ant, mindestens in der Version 1.8. Es
werden folgende Ant-Targets unterstuetzt:

 - compile: Kompiliert die Quellen des Packages
 - jar: Baut das EJB-JAR
 - deploy: Deployed das EJB-JAR auf dem Server
 - run: Fuehrt das zugehoerige Client-Programm aus
 - undeploy: Entfernt das Modul vom Server
 - clean: Entfernt alle erzeugten Generate

Einige Clients unterstuetzen eine Reihe von Parametern.
Die in diesem Fall ueber das Buildskript wie folgt an
den Client weitergeleitet werden werden koennen:
   ant -Dmain.args="<Meine Parameter>" run

Zur korrekten Ausfuehrung der Buildskripte muss zunächst
die Datei common.build.xml an die persoenliche Arbeits-
umgebung angepasst werden. Im Normalfall reicht die
Angabe des Installationspfads des JBoss-Servers 6
in der Property jboss.home (Zeile 6). Grundvoraussetzung
ist die Installation einer Java-6-Entwicklungsumgebung.

Einige Beispiele innerhalb der Kochrezepte (kochrezepte.
openejb.*) benutzen JUnit zur Implementierung von Unit-Tests.
Die dafuer notwendigen Bibliotheken koennen unter
http://www.junit.org heruntergeladen werden. Das Verzeichnis,
indem JUnit installiert wurde, muss schliesslich noch in
Zeile 5 innerhalb der build.xmls der beiden Beispiele angegeben
werden.

Fuer einige Beispiele sind im Buch zusaetzliche Informa-
tionen enthalten, die zum Verstaendnis notwendig sind.
