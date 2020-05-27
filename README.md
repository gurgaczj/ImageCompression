Image compression algorithm based on k-means algorithm.

Usage
```java
java -jar ImageCompression.jar path_to_original_image output_path number_of_clusters save_passes(true/false)
```

example usage:
```java
java -jar ImageCompression.jar F:\images\image.jpg F:\output\img.jpg 20 true
```

Number of clusters must be grated or equal to 4.