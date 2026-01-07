# kotlinx-serialization XML rules
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt # core serialization annotations

# Keep kotlinx-serialization runtime classes
-keep class kotlinx.serialization.** { *; }

# Keep XML serialization classes
-keep class nl.adaptivity.xmlutil.** { *; }

# Keep @Serializable classes
-keepclasseswithmembers class **.*$Companion {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep serializers for XML data classes
-keep,includedescriptorclasses class com.tidal.sdk.eventproducer.network.service.**$$serializer { *; }
-keepclassmembers class com.tidal.sdk.eventproducer.network.service.** {
    *** Companion;
}

# Keep annotation classes used in XML mapping
-keep class kotlinx.serialization.Serializable
-keep class nl.adaptivity.xmlutil.serialization.XmlSerialName
-keep class nl.adaptivity.xmlutil.serialization.XmlElement
