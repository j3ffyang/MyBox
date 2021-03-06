package mara.mybox.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import mara.mybox.fxml.FxmlControl;
import mara.mybox.value.AppVariables;
import mara.mybox.value.CommonValues;

/**
 * @Author Mara
 * @CreateDate 2018-6-13 8:14:06
 * @Description
 * @License Apache License Version 2.0
 */
public class AboutController extends BaseController {

    @FXML
    private Label version, date;
    @FXML
    private Hyperlink userGuideLink;

    public AboutController() {
        baseTitle = AppVariables.message("About");
    }

    @Override
    public void initializeNext() {
        version.setText(CommonValues.AppVersion);
        date.setText(CommonValues.AppVersionDate);
        userGuideLink.setText("https://github.com/Mararsh/MyBox/releases/download/v"
                + CommonValues.AppDocVersion + "/MyBox-UserGuide-" + CommonValues.AppDocVersion
                + "-Overview-" + AppVariables.getLanguage() + ".pdf");

        FxmlControl.miao5();

    }

}
