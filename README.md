<h1 align="center">
<img src="/assets/icon.png" alt="Icon" width="100" height="100">
<br>
BadMacro
<br>
</h1>

<!--## NOTICE
> :warning: :warning: :warning: **WARNING:** This project is no longer maintained; there may be bugs. Feel free to fork this repository, pull requests *may* be accepted. :warning: :warning: :warning:
-->
## What is BadMacro
BadMacro is a macro. Literally. [Search what a macro is...](https://www.google.com/search?q=what+is+a+computer+macro)

## Download
### Cloning
You can clone the repository by doing the following:  
`git clone https://github.com/itsmarsss/BadMacro.git`  
You can run it in an IDE or build it into a jar, up to you.
### Lazy download
If you're a lazy bum like me, head over to [[releases]](https://github.com/itsmarsss/BadMacro/releases), and download the most recent version.

Download the `.exe` if you're running Windows, otherwise download the `.jar`. Both versions **REQUIRE** at least Java 11 installed to function.

### Unexpected File
The program may create a `.dll` file. It is *not* malicious, at least not to my knowledge. This is created by a library used to register key binds called [JNativeHook](https://github.com/kwhat/jnativehook).  
`You can find this file in either the running directory or user temp folder.`

## Documentation
### Bad macro home
| Element       | Explanation                                                                                                  |
|---------------|--------------------------------------------------------------------------------------------------------------|
| Macros List   | A list of all macros, the first one ("KILLKEY") cannot be removed, it is used to terminate the running macro |
| New           | Create a new macro, enter a name for the macro on popup                                                      |
| Export/Import | Export or Import a macro in the form of a `.txt` file                                                        |
| Edit          | Edit selected macro                                                                                          |
| Delete        | Delete selected macro (Cannot be undone)                                                                     |
| Run           | Run selected macro                                                                                           |
| Stop          | Terminate running macro          

### Editing macro
| Element       | Usage                                                                                                                                                                                                        |
|---------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Macro name    | Change macro name                                                                                                                                                                                            |
| Sequence list | Sequence of events for this macro                                                                                                                                                                            |
| Bind          | Bind a key to this macro, click field and type something to bind (backspace to remove)                                                                                                                       |
| Insert        | Insert sequence, select sequence item in popup and follow instructions                                                                                                                                       |
| Remove        | Remove selected sequence item (cannot be undone)                                                                                                                                                             |
| Move Up       | Move selected sequence up by one                                                                                                                                                                             |
| Move Down     | Move selected sequence down by one                                                                                                                                                                           |
| Edit          | Edit selected sequence item                                                                                                                                                                                  |
| Duplicate     | Duplicate sequence item                                                                                                                                                                                      |
| Save          | Save all macro edits                                                                                                                                                                                         |
| Cancel        | Cancel all macro edits                                                                                                                                                                                       |
| Run Type      | 1. Single - Runs only once<br/>2. Repeat until stopped - Runs until terminated by `KILLKEY` macro or `STOP` button<br/>3. Repeat - Repeats a certain amount of times (can be edited in the field next to it) |


<!--## Video
<p align="center">Making of the program: https://www.youtube.com/watch?v=-sUVFuqVBdU</p>

[![Image Link](https://img.youtube.com/vi/-sUVFuqVBdU/maxresdefault.jpg)](https://www.youtube.com/watch?v=-sUVFuqVBdU)
-->