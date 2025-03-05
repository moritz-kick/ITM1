import DelibJava.DelibJNI64;

public class BSUSBExample {
    public static void main(String[] args) {
        // 1) Modul öffnen (BS‑USB mit 16 DI & 16 DO)
        int handle = DelibJNI64.DapiOpenModule(DelibJNI64.BS_USB_16, 0);
        if (handle == 0) {
            System.err.println("Modul konnte nicht geöffnet werden!");
            return;
        }
        System.out.println("BS‑USB Modul geöffnet (Handle = " + handle + ").");

        // 2) Digitalen Ausgang 1 einschalten (auf HIGH setzen)
        DelibJNI64.DapiDOSet1(handle, 1, 1);  // setzt Ausgang Kanal 1 auf 1 (AN)
        System.out.println("Ausgang 1 wurde auf AN gesetzt.");

        // 3) Digitalen Eingang 1 auslesen
        int value = DelibJNI64.DapiDIGet1(handle, 1);
        System.out.println("Eingang 1 Zustand: " + (value == 1 ? "AN" : "AUS"));

        // 4) Modul schließen
        DelibJNI64.DapiCloseModule(handle);
        System.out.println("BS‑USB Modul geschlossen.");
    }
}
