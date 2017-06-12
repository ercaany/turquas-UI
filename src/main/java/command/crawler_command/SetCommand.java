package command.crawler_command;

import admin.CrawlerAdmin;
import command.AbstractCommand;
import command.Command;

/**
 * Created by mustafa on 24.04.2017.
 */
public class SetCommand extends AbstractCommand implements Command {
    public boolean execute(String[] parameter) {
        if(!validateParameter(parameter))
            return false;

        if(CrawlerAdmin.crawlerParameterMap.get(parameter[1]) == null) {
            System.out.println(parameter[1] + " isimli parametre yok");
        } else {
            try {
                int value = Integer.parseInt(parameter[2]);
                CrawlerAdmin.crawlerParameterMap.put(parameter[1], value);
            } catch (NumberFormatException ex) {
                System.out.println("Lütfen sayısal bir değer giriniz.");
                ex.printStackTrace();
                return false;
            }
        }

        return true;
    }

    protected boolean validateParameter(String[] parameter) {
        return parameter.length == 1;
    }
}
