## BOARD GAME MANAGER PROJECT

# Introducción

El proyecto Board Game Manager trata de crear una aplicación que permita tanto a usuarios como administradores crear, 
modificar y borrar juegos de mesa, así como diseñadores, ilustradores, editoriales además de poder tratar con estos 
datos se pretende alcanzar una funcionalidad más, poder crear partidas de juegos de mesa, a las que se puedan 
apuntar jugadores dando una fecha, un lugar y registrándose estos en la base de datos.

Cada una de las anteriores pretende conseguir:
●	Añadir a la base de datos esa entidad
●	Actualizar la información de esta
●	Eliminar el registro de todos sus datos en las tablas vinculadas

El objetivo final es permitir a un usuario una visualización rápida de un gestor de partidas, 
así como consultar datos de un juego de mesa concreto.

# GUÍA RÁPIDA DE USO

Imprescindible tomar connection.dist cambiar extensión a connection.xml y asignar los datos de tu base de datos a los
parámetros que contiene para que la pueda cargar el programa.

Ejemplo:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<connection>
    <server>localhost</server>
    <port>3306</port>
    <dataBase>boardgames</dataBase>
    <user>root</user>
    <password>root</password>
</connection>
```

# Sin uso de IA

Proyecto completo realizado sin utilizar ningún sistema de LLM para un mayor aprendizaje y reconocimiento de programación, esto provoca un poco más de lentitud en algunas de las tareas, sin embargo también ayuda a conocer mejor el código.

----------------------------------------------------------------------------------------------------------------------------------

# Introduction

This project Board Game Manager aims to create an app to create, join and manage matches for board games, allowing the users to search
a match join and see when it's about to begin.

It also has the functionality to add board games with already determined designers, illustrators and publishers.
The administrator has more privileges that the normal user and can manage these:
- Designers
- Illustrators
- Publishers
- Matches
- Players
- Board games

The user can't see the first three. 


# QUICK GUIDE

Is indespensable to take connection.dist and change the extension to connection.xml, then you can edit it and put the data of your database (server, port, database, user, password)

Example:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<connection>
    <server>localhost</server>
    <port>3306</port>
    <dataBase>boardgames</dataBase>
    <user>root</user>
    <password>root</password>
</connection>
```

# NO AI use, 100% human-made

The project was made without using any LLMs, which can make a little harder some works, specifically the repetive ones, but I prefer to not use it, one of the reasons to do so it's to a better understanding on the code and all its methods.
