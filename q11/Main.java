package q11;

public class Main {
    public static void main(String[] args) throws Exception {
        Validator validator = new Validator();
        if (args.length < 1) {
            System.out.println("try: java <main_class> <xml_path>");
            System.out.println("try: java <main_class> <xml_path> <dtd_path>");
            System.exit(1);
        }

        String XMLPath = args[0];
        String DTDPath = null;
        if (args.length >= 2) {
            DTDPath = args[1];
        }
        validator.validateXMLWithDTD(XMLPath, DTDPath);
    }
}
