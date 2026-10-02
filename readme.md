
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

et voila domcn neotre deuxime microservice
![img_6.png](images/img_6.png)
on met netiites la aussi et product class