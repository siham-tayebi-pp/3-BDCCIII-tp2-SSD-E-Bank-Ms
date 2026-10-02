
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