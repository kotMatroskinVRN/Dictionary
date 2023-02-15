package home.fifteen.dictionary.utils.commandline;


import home.fifteen.dictionary.utils.coder.EncoderBase64;
import home.fifteen.dictionary.utils.logger.ColorfulLogger;

import java.io.File;

public enum KeyValueOption {

    encode("example.dct"){
        @Override
        void action() {
            File file = new File(value);

            boolean factor = file.isFile() && file.getName().equals(value);

            if(factor) {
                EncoderBase64 encoder = new EncoderBase64(value);
                encoder.initFileInfo();
                encoder.encode();

            }else{
                String string = String.format(
                        "\nWrong File Name : %s\nPlease specify file name from working directory." ,
                        value
                );
                logger.printError( string );
            }

            System.exit(0);
        }

        @Override
        void setDefaultValue() {
            value = "example.dct";
        }
    },

    ;

    private static final ColorfulLogger logger = ColorfulLogger.getLogger();
    private static String[] args;


    String value ;


    KeyValueOption(String value) {
        this.value = value;
        setDefaultValue();
    }



    public static void init(String[] args){
        KeyValueOption.args = args;

        for(int i=0;i<args.length;i++){
            String option = args[i];
            logger.printInfo(option);
            if(option.startsWith("-")){
                parseOption(option.substring(1), i);
            }
        }
    }

    abstract void action();
    abstract void setDefaultValue() ;


    public String getValue() {
        return value;
    }

    private static void parseOption(String option , int number) {
        for (KeyValueOption entry : KeyValueOption.values()){
            logger.printVerbose(entry.name() , entry.getValue());

            if(entry.name().equals(option)  && isValidOption( option ,number+1) ){

                    entry.value = args[number+1];
                    logger.printInfo(entry.name() , entry.getValue());
                    entry.action();

            }

        }
    }

    private static boolean isValidOption(String option, int i) {
        if(i>=args.length){
            logger.printError(option , "no value for option");
            return false;
        }
        if(args[i].startsWith("-")){
            logger.printError(option , "no value for option");
            return false;
        }


        return true;
    }

}
