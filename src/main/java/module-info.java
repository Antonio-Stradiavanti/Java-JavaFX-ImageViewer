// Объявление модуля, в объявлении модуля указывается имя модуля и отношение модуля с другими пакетами и модулями.
// Дескриптор модуля -> Объявления модулей записываются в виде инструкций в файле исходного кода java с именем module-info.
module ru.manannikov.imageViewer {
    requires java.prefs;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.swing;
    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    requires org.apache.commons.io;
    requires org.apache.commons.lang3;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.dataformat.xml;
    requires com.twelvemonkeys.imageio.core;
    requires com.twelvemonkeys.imageio.metadata;
    requires com.twelvemonkeys.imageio.bmp;
    requires com.twelvemonkeys.imageio.jpeg;
    requires com.twelvemonkeys.imageio.tiff;
    requires com.twelvemonkeys.imageio.psd;
    requires imgscalr.lib;
    requires metadata.extractor;

    // Текущий модуль разрешает доступ во время выполнения к пакету ru.manannikov.imageViewer следующим модулям (to -> список модулей, для которых пакет является открытым):
    opens ru.manannikov.imageViewer to javafx.fxml, javafx.base, javafx.graphics;
    // Имя пакета задается относительно текущего модуля.
    exports ru.manannikov.imageViewer;
}
