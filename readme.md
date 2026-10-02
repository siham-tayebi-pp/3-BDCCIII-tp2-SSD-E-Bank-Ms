
microservcie sont fairte pour repduire complecite des sys et avce deifernet ltehcnologie
t en base on besoin de 3 microsrcive
1 gaeway qui perme denv kes rq
2 Discovery
enreigsite nom et domaine des microservice via le regiser cad si connait nom microsevrice j epux connature adres
pour centraliser config des micserrvice  avec configuration sevricce qui rassmble tt fich de config pour que si ya maj tt servcie vont la srecevoir
cahllenge comemnt faire communictaion entre micro
synchrone ou asynchrone
sysnchorem base sur web servoce : soap rest graphsql , grpc , mcp la derniere verison pour egnate ai
asynchrone : brokers comme Kafka , rabbit mq , bactive mq sont de sbrokrs utilsi par les ezse pour permemter au ese dachnger des msg de mainere asyns
t pour sercuute ya des proptocole come aouath2 , ioidc mais loutil le plus utilsie par ese c keylcloack
dans arhci si ya req ca passe par gqtewya la Gateway dconnei le nom et cherch adresse chez Discovery servcie pour cherhc eladdresse
mais commn Discovery va scherhce car chaue srvcie demarre io fiat regiter et son nom e url senregistre et un fois neded on le reccupere
et la Gateway envoie le req vers le bon microservice
mais si ya prob de monte en chareg la sol c la sclabiltie horzonale cad demarre le servcie dans plusiauer s;machine
donc qund la Gateway demnade lurl il bva recucperer une liste et la geaeway va faire lequibrage de chargeentre l zinstnaces
docn la gwteway jour droel de sys de routage et equibrage de cahrge
DOnc la demo c sur comment aimpnmennter cet arhci vai spring boot spring cloud etc
on suppose on va dev uen app qui se base sur 3 microservcie
Customer servcie , billinng , inventory au lieu de faire t dan smeme app
on va voire comem crer un simpel microservice comment cionfigurer sys routage de manière statique pusi en dynmaique via dsicovery service
pis on passe a la lisaicon e le comunation etnre ces microsevrice via Framework open friyn
mais ici quen don cree un microservcie on va pas respecter les normes on va el fiare rapdiemennt docn on créer notre projet
![img.png](images/img.png)


on ajoute new moduel uqi projet spring boot par epxle custome rservcie
![img_1.png](images/img_1.png)
tjsr on charche dna sliste maven au ca sou un ms n pas ajoute on  lajoyte nosu mem
![img_2.png](images/img_2.png)
on pass acreer netotes etc

voila
package net.tayebi.customerservice.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok .*;
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Customer {
@Id @GeneratedValue
private Long id;
private String name;
private String email;
}
 on apaase a creer notre repository pour les meth de manin de customer 

pusi on abesoin de web service mais on travial spring dtat rets en ajouuter repository rest resosurce pour dmearre r auto web service restfull pour acceder a tt meth de cette classe auto
package net.tayebi.customerservice.repositories;

import net.tayebi.customerservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface CustomerRepository extends JpaRepository<Customer,Long> {

} pour demare auto web service restfull
on va au main et en ree beans dans commandline runner
comemca package net.tayebi.customerservice;

import net.tayebi.customerservice.entities.Customer;
import net.tayebi.customerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(CustomerRepository repository, CustomerRepository customerRepository) {
        return args -> {
            customerRepository.save(Customer.builder().name("siham") .email("siham@gmail.com").build());
            customerRepository.save(Customer.builder().name("imane") .email("imane@gmail.com").build());
            customerRepository.save(Customer.builder().name("fouzia") .email("fouzia@gmail.com").build());
            customerRepository.save(Customer.builder().name("hamid") .email("hamid@gmail.com").build());



        }
    }

}

on passe au config fiel de microsevrice

spring.application.name=customer-service
server.port=8081
spring.datasource.url=jdbc:h2:mem:customers-db
spring.h2.console.enabled=true
on met dicsrovy enabled flase car chque microservice doit se dconencter a dicovery pour scoker name et url mais pour le moemmnt in a uan seul microcsevrice

spring.cloud.config.enabled=false
spring.cloud.discovery.enabled=false
onm exect app ca marche bien on consulte bd
et voial mes customer sont bein dans mon bd

![img_3.png](images/img_3.png)
on passe a tetsre si mon web servcie marche bien  vai
http://localhost:8081/customers
![img_4.png](images/img_4.png)
et donc il marche bein
la on put ajouter suppr modif by id tt
by id ca marche aussi  http://localhost:8081/customers/1
![img_5.png](images/img_5.png)
sprinfg data rets conieent embedde avec links aux autre ressource ca c dans spring dta rest
docn 1 er microservcie customer-servcie done on passe a ajute a laute smssmicro service inventory-service

avc mem depandnces

rest repositories c une depnndnace qui demarre les  
web sevrices restfull ayto
et depnndnance sdes arhci micro comme
eureka deicory  car chhauqe  micir doit senregistere ver discover pour que eurake communiuq auto avec discorveyr

pusi spting config client
pour contatctr servcie d config pour cherhcer config
puis actuator pour fiare monitoring des ms
cad une fois un est demarre n veut svaoir son ettat
de mémoire caklsse careg etc



et voila domcn neotre deuxime microservice
![img_6.png](images/img_6.png)
on met netiites la aussi et product class

package net.tayebi.inventoryservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok .*;

@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Product {
@Id @GeneratedValue
private Long id;
private String name;
private double price;
private int quantity;
}
puis on passe a notre repository 

package net.tayebi.inventoryservice;

import net.tayebi.inventoryservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {
}
on cree de sproducts en beans dan smian
package net.tayebi.inventoryservice;

import net.tayebi.inventoryservice.entities.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(ProductRepository productRepository) {
        return args -> {
          productRepository.save(Product.builder()
                          .name("Computer")
                          .price(8000)
                          .quantity(12)
                          .build());
            productRepository.save(Product.builder()
                    .name("Printer")
                    .price(3000)
                    .quantity(5)
                    .build());
            productRepository.save(Product.builder()
                    .name("Smartphone")
                    .price(10000)
                    .quantity(10)
                    .build());
            productRepository.save(Product.builder()
                    .name("Mouse")
                    .price(100)
                    .quantity(30)
                    .build());

        };

    }

}
 on va kes trovr en bd
mais avent de tetsert on va faire confi meme ce micro sevrice
spring.application.name=inventory-service
server.port=8082
spring.datasource.url=jdbc:h2:mem:products-db
spring.h2.console.enabled=true

spring.cloud.discovery.enabled=false
spring.cloud.config.enabled=false
 puis on demarre aussi sevrice inventory et on teste
 la on notre web servcie aussi creer auto 

on tets on va le toruveer dan sobd et mem on essaye bnotre web servcie 
![img_7.png](images/img_7.png) on a tetse http://localhost:8082/products c bon tt products apaita n
on tetst bd :[poru vour la ] 
![img_8.png](images/img_8.png) http://localhost:8082/h2-console/login.do?jsessionid=e6974670a80e0bbda707944fbd1d9f24
on a suusi tst product  by id et  c bon
http://localhost:8082/products/1
 ![img_9.png](images/img_9.png)  c bon 
donc on tets actuator qui permet de faire monitorin  on tets
http://localhost:8082/actuator
ca affiche les optiosn

![img_10.png](images/img_10.png)

on aussi helath pour monitoring
http://localhost:8082/actuator/health 
c bon la on a up sapparait
![img_11.png](images/img_11.png)
omn jate properriite dna appl config pour ue tu me dispose tts ce que dispore acuaor

management.endpoints.web.exposure.include=*
 on redemmare note inventory  sevice 
 on reapplie actualtor  
on fait  http://localhost:8082/actuator 
la on voit un large list
![img_12.png](images/img_12.png)
on fiat   http://localhost:8082/actuator/beans
la on voit beans
![img_13.png](images/img_13.png)
la on voit tt nos clas charge en moemeorie 
on peut voire aussi trace cahce et iansi de suite
le var dencv via /env
donc actuator n gnerlae dser pour faire le monitoring  surtt  health est plus importnate 
pour debelopper de sarchi microsevice on cva utilsier de socntenurs cokcee et qund on les utilsie a un momemn donne  on va utilsir un outil deocherstatrion qui est kubrnitties e celui la si un sevrice tmobe en panne c lui quie le dmearre
et pour savoir achque seconde il envoire un req vers / health si up c bon sinn docn il va perceboir que micros rvice ne marhc epa set le demarre
 donc quon on va cnetrlaiser config
 on va fiare commit  et appelr acuator avec erefrssh qui contace servcie de config et va y pour chque les modifs 
donc on aura besoin dque de helath c pas besoin daciver tt que ceux aui nosu intersse comme helath et refresh
management.endpoints.web.exposure.include=health , refresh

jusque momemnt on a cree deux mis un avec 8081 et 2 avc 8082 ports
rest rpository reste juste pour de soperations crud pas pour couche metier etc
apr exle en regle metier n save on doit verifier cad rest data que pour basics rapide pratiqu doit etre avec de srgles nmetier etc
spring data rets que poru repdiite etc s
maintnen aon cree microservice
maintnen ton va cree gqateway poru se fiare on va ajoute un nnv micro servcie on va le nomme 
gateway-service avec recaive gateway dependandy and eureka et actuator

reacive gateway

pour benifiecier des virtuel thread  afin de nous permett dutilsier les urt thread

![img_14.png](images/img_14.png)
donc gatweway sevrice bien creer
donc on passe config route de faocn sttaiue dan sun fich app.yaml
car sping peut etre config soit via .properties ou .yml car ca va avec docker ansible etc
dans spring boot
cahuqe route est deifn
par uri  qui est url
pusi indiquer predicae cad qunns envoye req a dctt eroute et parmi peredicat ya path
predicat cad conditons a satidsafiare pour etre envoye ver smicrosrvice

pusi filters  qund gateway envoie req a ms avent il peut ajouter des headers token etc   avnt denvoyer la req
pour des raison de securite par exple

spring:
cloud:
gateway:
server:
webflux:
routes:
- id: r1
uri: http://localhost:8081
predicates:
- Path= /customers/**
- id: r2
uri: http://localhost:8082
predicates:
- Path= /products/**
dans fich config on ajoute aussi port pour gatwaye service
- spring.application.name=gateway-service
  server.port=8888
  spring.cloud.discovery.enabled=false

on teste docn la roue et on va voir si notre gatwaaye m,amrche bien
http://localhost:8888/actuator/health t voila donc c en up 




et on tetste apres
 ![img_15.png](images/img_15.png) dnc notre gateway marche 
maintnent on tets  pour voir si va ne deirigier vers les routes excte
http://localhost:8888/customers
![img_16.png](images/img_16.png) dnc nos customers sont bein affiche
http://localhost:8888/customers/1 donc la note customer est afcihe avce succes
![img_17.png](images/img_17.png)
 on passe a teser
http://localhost:8888/products
donc c nbein marhc tt products affiche
![img_18.png](images/img_18.png)
car  cnotre gateway qui decide ou le deriger
ca c pour statique mais genralement on cnaonneit pas ladress de microservcie et la sol c duitlsier discovery car c lui quei enregsitre nom avec @ ip du miscro service 
donc on ajoute module discovery-service
 avec depnedency eureka server  server qui est implmenter de dsicovery qui est forunit par netflix car sont lun des permeir qui eon implemnter architecture microservice et ca tombe jamias en panne
mais aussi consul discorveyr mais nous on utilsie eureka deisvcovey et aussi actuator
![img_19.png](images/img_19.png)
on passe  axtive r dsipcovery srvie  via  dna smain de discoveyr
package com.example.discovery_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServiceApplication.class, args);
	}

}

on a cree micreocsr pusi gateway puis config suys routage puis deicsvery srvice et maintnnetn on fait  creer euraka server
pusi dna spalication properties on emt
spring.application.name=discovery-service
server.port=8761
eureka.client.fetch-registry=false
eureka.client.register-with-eureka=false
 car on abesoin que sune seul discovery si on a bcp qui ont besoin de communiquer entre eux eon peut donc les activer
on exefc discovery donc
la on vois ;a lsite des services enregsitre pour le moment il yen a aucun  car on a descaive loperation de  register donc on revient a lactiver  dnas custmr servrvice 
et eutre
http://localhost:8761/
![img_20.png](images/img_20.png) 
on les sacive donc
spring.cloud.discovery.enabled=true
 dans custerme microservice et aussi autre micro customer inventory et gaeway ett on restart 
et o  acualise notre page

et voila donc dnos 3 servcie s
![img_21.png](images/img_21.png)
mais maintnen on veut fair
que ses mis senregistre eux meme don  on supp  ou on rempleac les @ ip dans notre .yaml par 
ln://name-service cad une fois req /cusotmer envpie vers serveice scustomers et meme pour products
la gateeway va envoyer nom vers dicrober et lui la donne lurl  lb c load balancer
si bcp dinstance domnc il va essayer dequilbre la charge
donc gateway va jouer deux role rouutage et de load banlacer si ya bcp instnce
lb://CUSTOMER-SERVICE
lb://INVENTORY-SERVICE
puis on remarre la gatewaye 
apres poru faire ca demarre ca arrter lautre on va utulsier docker  avec docker composer docker comup demmare tt down down tt c c le devops
car c fatiguant
 c ca donc config de gateqay nouvvel:
spring:
cloud:
gateway:
server:
webflux:
routes:
- id: r1
#              uri: http://localhost:8081
              uri: lb://CUSTOMER-SERVICE
              predicates:
                - Path= /customers/**
            - id: r2
              uri: lb://INVENTORY-SERVICE
#              uri: http://localhost:8082
              predicates:
                - Path= /products/**
on redmemearre donc notre gatweway et voila donc ca va marche rpour tt products custoemrs  tt
http://localhost:8888/products
![img_18.png](images/img_18.png)
http://localhost:8888/products/1
![img_22.png](images/img_22.png)
http://localhost:8888/customers
![img_16.png](images/img_16.png)
http://localhost:8888/customers/1
![img_17.png](images/img_17.png)




mais on peur aussi faire ca que dan sapp.properties pas jutememnt.yaml en faisnat 
masi sca ser adififciel a lire s et bcp de code aml c lisible
spring.cloud.gateway.server.webflux.routes[0].id=r1
spring.cloud.gateway.server.webflux.routes[0].uri=lb://CUSTOMER-SERVICE
spring.cloud.gateway.server.webflux.routes[0].predicates[0].name=Path
spring.cloud.gateway.server.webflux.routes[0].predicates[0].......

si on bevut pas config avec fch config on peut la mette avec java

 via un bean de rtype route locator dans main

package net.tayebi.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("r1", p -> p.path("/customers/**").uri("lb://CUSTOMER-SERVICE") )
                .route("r2", p -> p.path("/products/**").uri("lb://INVENTORY-SERVICE") )
                .build();
    }



}


ca amarche aussi  on tste t on renomme notre .yaml pur tester
![img_23.png](images/img_23.png) comem ca a.yaml n plus recunnu on passe a esr si nv meth amrhc ebien

http://localhost:8888/customers
![img_16.png](images/img_16.png)
http://localhost:8888/products
![img_18.png](images/img_18.png)
mais pour que tt  
http://localhost:8888/customers/1

http://localhost:8888/products/1
car avnt /** ca retnr eerr
![img_24.png](images/img_24.png)
pour les deux mais on va le fixer 
package net.tayebi.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("r1", p -> p.path("/customers/**").uri("lb://CUSTOMER-SERVICE") )
                .route("r2", p -> p.path("/products/**").uri("lb://INVENTORY-SERVICE") )
                .build();
    }



}

t donc c bon ca ,marche 

http://localhost:8888/customers/1
![img_17.png](images/img_17.png)

http://localhost:8888/products/1
![img_22.png](images/img_22.png)
donc on a deux cas soit crer bean ou faire via config application
mais pour la fiare sdynmiquement via nom servcie tq ;la en sa basnt usr name on recucper ladress
docn on deastice sys routtage staiqyue
on passe au sys de routge dynamique
on cree bean de type
et ce bean la qui va gegre le srotes

@Bean
public DiscoveryClientRouteDefinitionLocator dynamicRoutes(
ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp){

        return new DiscoveryClientRouteDefinitionLocator(rdc, dlp);
    }
donc pour tester on va mette
http://localhost:8888/INVENTORY-SERVICE/products
![img_27.png](images/img_27.png)
http://localhost:8888/INVENTORY-SERVICE/products/1
![img_28.png](images/img_28.png)
http://localhost:8888/CUSTOMER-SERVICE/customers
![img_25.png](images/img_25.png)
http://localhost:8888/CUSTOMER-SERVICE/customers/1
![img_26.png](images/img_26.png)
et volila donc notre sys de routage dynamique marhc ebien/
donc  engenrnal on va utilsier routage dynamique pour ceux qui sont enregsitere dans discory
et statueu pur cuex qui  sont nrgsitre dna siscovery
apr epxl appeler servcie de open ai comme il est pas enregistrer donc on va nmetre le statique ici