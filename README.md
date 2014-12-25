#### What is SoM ####

Swords of Magic is a Magic: the Gathering card collection database organizer. It's an online website where you can quicly edit your collection information, check pricing data or show your tradeable cards.

#### History ####

The project is created purely to learn Spring and AngularJS but ended up being good enough to release it as an open-source software. Tha name comes from the famous Swords cycle in the Scars of Mirrodin block.

http://archive.wizards.com/Magic/magazine/article.aspx?x=mtg/daily/arcana/631

#### Demo ####

The demo is located here:
http://rocky-hamlet-5351.herokuapp.com/

The admin user's username/password is admin/admin.

#### How to install ####

You need basic understanding of the Heroku hosting environbent to be able to install Swords of Magic. (More info at http://www.heroku.com/)

1. Clone the repository
2. Run heroku create
3. Run heroku addons:add mongolab
4. Push to Heroku
5. Go to Heroku's Dashboard and set JAVA_OPTS environment variable to something similar to this: ```-Xss512k -Dspring.data.mongodb.uri=mongodb://heroku_app32836660:xxx@yyy.mongolab.com:49150/heroku_app32836660``` where the spring.data.mongodb.uri comes from the MONGOLAB_URI config variable.
6. Go to mongolab's addon site and add a user to the user collection like this: 
```{
    "_class": "com.swords.model.User",
    "username": "admin",
    "password": "admin"
}```
7. Congratulation, your website is ready to use.

#### License ####

Swords of Magic is free for non-commercial. If you plan to use Swords of Magic in commercial environment please write me a private message for more information.

#### Thanks ####

For the flag icons thanks to:
http://www.icondrawer.com/

For the image slider library thanks to:
http://highslide.com/
