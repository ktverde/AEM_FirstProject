# Sample AEM project template

This is a project template for AEM-based applications. It is intended as a best-practice set of examples as well as a potential starting point to develop your own functionality.

## Modules

The main parts of the template are:

* core: Java bundle containing all core functionality like OSGi services, listeners or schedulers, as well as component-related Java code such as servlets or request filters.
* it.tests: Java based integration tests
* ui.apps: contains the /apps (and /etc) parts of the project, ie JS&CSS clientlibs, components, and templates
* ui.content: contains sample content using the components from the ui.apps
* ui.config: contains runmode specific OSGi configs for the project
* ui.frontend: an optional dedicated front-end build mechanism (Angular, React or general Webpack project)
* ui.tests: Selenium based UI tests
* all: a single content package that embeds all of the compiled modules (bundles and content packages) including any vendor dependencies
* analyse: this module runs analysis on the project which provides additional validation for deploying into AEMaaCS

## How to build

To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

To build all the modules and deploy the `all` package to a local instance of AEM, run in the project root directory the following command:

    mvn clean install -PautoInstallSinglePackage

Or to deploy it to a publish instance, run

    mvn clean install -PautoInstallSinglePackagePublish

Or alternatively

    mvn clean install -PautoInstallSinglePackage -Daem.port=4503

Or to deploy only the bundle to the author, run

    mvn clean install -PautoInstallBundle

Or to deploy only a single content package, run in the sub-module directory (i.e `ui.apps`)

    mvn clean install -PautoInstallPackage

## Testing

There are three levels of testing contained in the project:

### Unit tests

This show-cases classic unit testing of the code contained in the bundle. To
test, execute:

    mvn clean test

### Integration tests

This allows running integration tests that exercise the capabilities of AEM via
HTTP calls to its API. To run the integration tests, run:

    mvn clean verify -Plocal

Test classes must be saved in the `src/main/java` directory (or any of its
subdirectories), and must be contained in files matching the pattern `*IT.java`.

The configuration provides sensible defaults for a typical local installation of
AEM. If you want to point the integration tests to different AEM author and
publish instances, you can use the following system properties via Maven's `-D`
flag.

| Property | Description | Default value |
| --- | --- | --- |
| `it.author.url` | URL of the author instance | `http://localhost:4502` |
| `it.author.user` | Admin user for the author instance | `admin` |
| `it.author.password` | Password of the admin user for the author instance | `admin` |
| `it.publish.url` | URL of the publish instance | `http://localhost:4503` |
| `it.publish.user` | Admin user for the publish instance | `admin` |
| `it.publish.password` | Password of the admin user for the publish instance | `admin` |

The integration tests in this archetype use the [AEM Testing
Clients](https://github.com/adobe/aem-testing-clients) and showcase some
recommended [best
practices](https://github.com/adobe/aem-testing-clients/wiki/Best-practices) to
be put in use when writing integration tests for AEM.

## Static Analysis

The `analyse` module performs static analysis on the project for deploying into AEMaaCS. It is automatically
run when executing

    mvn clean install

from the project root directory. Additional information about this analysis and how to further configure it
can be found here https://github.com/adobe/aemanalyser-maven-plugin

### UI tests

They will test the UI layer of your AEM application using Selenium technology. 

To run them locally:

    mvn clean verify -Pui-tests-local-execution

This default command requires:
* an AEM author instance available at http://localhost:4502 (with the whole project built and deployed on it, see `How to build` section above)
* Chrome browser installed at default location

Check README file in `ui.tests` module for more details.

## ClientLibs

The frontend module is made available using an [AEM ClientLib](https://helpx.adobe.com/experience-manager/6-5/sites/developing/using/clientlibs.html). When executing the NPM build script, the app is built and the [`aem-clientlib-generator`](https://github.com/wcm-io-frontend/aem-clientlib-generator) package takes the resulting build output and transforms it into such a ClientLib.

A ClientLib will consist of the following files and directories:

- `css/`: CSS files which can be requested in the HTML
- `css.txt` (tells AEM the order and names of files in `css/` so they can be merged)
- `js/`: JavaScript files which can be requested in the HTML
- `js.txt` (tells AEM the order and names of files in `js/` so they can be merged
- `resources/`: Source maps, non-entrypoint code chunks (resulting from code splitting), static assets (e.g. icons), etc.

## Maven settings

The project comes with the auto-public repository configured. To setup the repository in your Maven settings, refer to:

    http://helpx.adobe.com/experience-manager/kb/SetUpTheAdobeMavenRepository.html
    
## EndPoints

    /bin/keepalive/userService
    
Get - Coleta uma lista de usu??rios cadastrados no sistema.
    
- `Par??metros`: nenhum -> Exibe todos os produtos cadastrados.
- username -> Opcional - Filtra apenas o usu??rio ao qual foi informado o username.
   
Post - Cadastra um usu??rio no sistema.
   
- `Body`: User -> Obrigat??rio - Necess??rio um json de usu??rio para ser cadastrado, no corpo da requisi????o. Pode ser inserida uma lista de usu??rios tamb??m.

Delete - Deleta um usu??rio ou uma lista de usu??rios cadastrados no sistema.
    
- `Body`: User -> Obrigat??rio - Necess??rio um json de usu??rio, contendo pelo menos o username ou o id do usu??rio para ser deletado, no corpo da requisi????o. Pode ser inserida uma lista  tamb??m.

Put - Atualiza um usu??rio no sistema.
   
- `Body`: User -> Obrigat??rio - Necess??rio um json de usu??rio para ser atualizado, no corpo da requisi????o. O json do usu??rio apenas atualizar?? as informa????es divergentes, por exemplo, ao enviar um json vazio, nenhuma altera????o ser?? feita. Ao mandar um json apenas com um username, apenas o username ser?? atualizado, mantendo as outras informa????es.
- `Par??metros`: username -> Obrigat??rio - Indica qual usu??rio ser?? atualizado.

-----------

    /bin/keepalive/productService
    
Get - Coleta uma lista de produtos cadastrados no sistema.
    
- `Parametros`: nenhum -> Exibe todos os produtos cadastrados.
- pId -> Opcional - Filtra apenas o produto ao qual foi informado o id.
- searchFor -> Opcional - Filtra a lista por uma palavra espec??fica informada. 
- order -> Opcional - Ordena a lista, em ordem crescente de pre??os.
   
Post - Cadastra um produto no sistema.
   
- `Body`: Product -> Obrigat??rio - Necess??rio um json de produto para ser cadastrado, no corpo da requisi????o. Pode ser inserida uma lista de produtos tamb??m.

Delete - Deleta um produto ou uma lista de produtos cadastrados no sistema.
    
- `Body`: Product -> Obrigat??rio - Necess??rio um json de produto, contendo pelo menos o seu id para ser deletado, no corpo da requisi????o. Pode ser inserida uma lista  tamb??m.

Put - Atualiza um produto no sistema.
   
- `Body`: Product -> Obrigat??rio - Necess??rio um json de produto para ser atualizado, no corpo da requisi????o. O json do produto apenas atualizar?? as informa????es divergentes, por exemplo, ao enviar um json vazio, nenhuma altera????o ser?? feita. Ao mandar um json apenas com um pre??o, apenas o pre??o ser?? atualizado, mantendo as outras informa????es.
- `Par??metros`: pId -> Obrigat??rio - Indica qual produto ser?? atualizado.

-----------

    /bin/keepalive/ticketService
    
Get - Coleta uma lista de tickets cadastrados no sistema.
    
- `Parametros`: nenhum -> Exibe todos os tickets gerados.
- id -> Opcional - Filtra apenas o ticket ao qual foi informado o id.
- uId -> Opcional - Filtra apenas os tickets que est??o relacionados ao id de usu??rio informado por esse par??metro.
   
Post - Gera um ticket no sistema.
   
- `Body`: Ticket -> Obrigat??rio - Necess??rio um json de ticket para ser gerado, no corpo da requisi????o. Pode ser inserida uma lista de tickets tamb??m. Obrigat??rio apenas um id v??lido de produto, id v??lido de usu??rio, e uma quantidade desejada para compra, em formato json.

-----------

    /bin/product/user/report
    
Get - Gera um relat??rio de compras de um determinado usu??rio, informado por par??metro.
    
- `Parametros`:
- username -> Obrigat??rio - Informa um relat??rio do username informado.
- uId -> Obrigat??rio - Informa um relat??rio do id de usu??rio informado.

Apenas um dos par??metros ?? obrigat??rio ser informado para o relat??rio ser gerado.

Caso os dois par??metros sejam informados, o relat??rio ser?? gerado para dois usu??rios, caso os mesmos sejam diferentes.
Caso sejam iguais, ser?? informado um relat??rio ??nico.

-----------
