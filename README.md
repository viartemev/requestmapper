<div align="center">
    <a href="https://plugins.jetbrains.com/plugin/9567-request-mapper">
        <img src="./src/main/resources/META-INF/pluginIcon.svg" width="320" height="320" alt="logo"/>
    </a>
</div>
<h1 align="center">Request Mapper</h1>
<p align="center">Request mapper is a plugin for IntelliJ IDEA for quick navigation to URL mapping declarations.</p>

<p align="center"> 
<a href="https://travis-ci.org/viartemev/requestmapper"><img src="https://travis-ci.org/viartemev/requestmapper.svg?branch=master"></a>
<a href="https://plugins.jetbrains.com/plugin/9567-request-mapper"><img src="https://img.shields.io/jetbrains/plugin/d/9567-request-mapper.svg"></a>
<a href="https://plugins.jetbrains.com/plugin/9567-request-mapper"><img src="https://img.shields.io/jetbrains/plugin/v/9567-request-mapper.svg?maxAge=2592000"></a>
<a href="https://codecov.io/gh/viartemev/requestmapper"><img src="https://codecov.io/gh/viartemev/requestmapper/branch/master/graph/badge.svg"></a>
<a href="https://www.codetriage.com/viartemev/requestmapper"><img src="https://www.codetriage.com/viartemev/requestmapper/badges/users.svg"></a>
<a href="https://snyk.io/test/github/viartemev/requestmapper?targetFile=build.gradle"><img src="https://snyk.io/test/github/viartemev/requestmapper/badge.svg?targetFile=build.gradle"></a>
</p>

### Plugin info
##### Supported annotations:
| Spring  | JAX-RS  | Micronaut  |
|:-:|:-:|:-:|
| ```@RequestMapping``` | | |
| ```@GetMapping``` | ```@GET``` | ```@Get``` |
| ```@PostMapping```  | ```@POST``` | ```@Post``` |
| ```@PutMapping``` | ```@PUT``` | ```@Put``` |
| ```@DeleteMapping``` | ```@DELETE``` | ```@Delete``` |
| ```@PatchMapping``` | ```@PATCH``` |  ```@Patch``` |
| | ```@OPTIONS``` |  ```@Options``` |
| | ```@HEAD``` | ```@Head``` |

### Contributors
Thanks to all people who have contributed to this plugin:
+ [Dmitry Chuiko](https://github.com/dchuiko)
+ [Paul Finkelshteyn](https://github.com/asm0dey) 

### Usage
- Keyboard shortcuts:
    - MacOS: ```Cmd + Shift + Back slash```
    - Linux: ```Ctrl + Shift + Back slash```
    - Windows: ```Ctrl + Shift + Back slash```
- Navigate (menu bar) -> Request Mapping
- Click ```Shift``` twice -> request mapping

![](art/requestmapper.gif)
