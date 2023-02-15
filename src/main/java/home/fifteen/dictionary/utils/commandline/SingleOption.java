package home.fifteen.dictionary.utils.commandline;


import home.fifteen.dictionary.utils.logger.ColorfulLogger;

public enum SingleOption {

    v{
        @Override
        public void action() {
            logger.enableVerbose();
            logger.printInfo( name() , "VERBOSE");
        }
    },

    h{
        @Override
        public void action() {
            Help help = new Help();
            help.printOut();
            System.exit(0);
        }
    },
    help{
        @Override
        public void action() {
            h.action();
        }
    },

    ;

    private static final ColorfulLogger logger = ColorfulLogger.getLogger();




    public static void init(String[] args){

        for(String option : args){
            if(isValidOption( option ) ){
                parseOption(option.substring(1));
            }
        }
    }

    abstract void action();

    private static void parseOption(String option ) {
        for (SingleOption entry : SingleOption.values()){
            logger.printVerbose( entry.name() );

            if(entry.name().equals(option) ){
                entry.action();
            }

        }
    }


    private static boolean isValidOption(String option) {
        return option.startsWith("-");
    }

}
