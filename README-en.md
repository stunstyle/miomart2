# miomart2

> Miomart2 is an app created to ease the workflow of small to medium grocery stores and to eliminate the need of manually writing on paper all bought/sold products and calculating their total values.
<hr>

## Features

- Maintain a database of all products, containing their names, wholesale and retail prices
- Maintain a database of records for every workday, where all wholesale products are added and the totals of both prices are calculated
- Report generation
- **[WIP]** Printing of reports

## Getting started
### Prerequisites
This app requires JRE 8+ and **Oracle** JDK 8+ for compilation.

*Note: at present time, OpenJDK compilation is not supported*
### How to install
Clone this repo:

`git clone https://github.com/stunstyle/miomart2`
### How to run
Run target/miomart2-1.0-SNAPSHOT.jar via double-click or from a terminal:

`java -jar miomart2-1.0-SNAPSHOT.jar`
### Usage
#### Start screen
On starting the app, users are presented with the Product DB Management screen:

![Start Screen](screens/add_product.png?raw=true "Start Screen")
It is used for adding and removing different products from the app’s DB.

![Removal](screens/remove_product.png?raw=true "Removal")
#### Browse/Edit records
To access this screen, users must first pick a date::
![Pick Date](screens/pick_date.png?raw=true "Pick Date")
![Browse/Edit records](screens/edit_record.png?raw=true "Browse/Edit")
If the user tries to add a product which does not exist in the Product DB, they are presented with the following alert:

![Alert](screens/edit_record_confirm.png?raw=true "Alert")

While inputting the desired product’s name on this screen, the user can access an extended search view by pressing **F4**.

#### Reports
![Reports](screens/create_report_view.png?raw=true "Reports")
![Generated Report](screens/created_report.png?raw=true "Generated report")

## Help
For any questions related to the use of the app and/or the code:

[stunstyle@yahoo.com](mailto:stunstyle@yahoo.com)



