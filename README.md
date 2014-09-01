Spark - a Sinatra inspired web framework
==============================================

Key Changes From Official Spark
-------------------------------

 * Avoids use of static functions and global state. While the use of static functions can be more convenient it is unsuitable for use within a servlet
environment where multiple web applications may attempt to configure Spark. Such use of static configuration may or may not work depending on the way the class loaders are structured.

 * Continues to support Java 6.

 * Does not provide an embedded server.