package home.fifteen.dictionary.utils.commandline;

import home.fifteen.dictionary.utils.file.StringResource;
import home.fifteen.dictionary.utils.logger.ColorfulLogger;

public class Help {


    public Help(){

    }



    public void printOut() {

        StringResource resource = new StringResource();
        String message = resource.resource2string("help.txt");
        ColorfulLogger.getLogger().printInfo(message);

    }
}
