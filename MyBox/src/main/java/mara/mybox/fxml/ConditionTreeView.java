package mara.mybox.fxml;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import static mara.mybox.value.AppVariables.logger;

/**
 * @Author Mara
 * @CreateDate 2020-04-19
 * @License Apache License Version 2.0
 */
public class ConditionTreeView extends TreeView {

    protected String finalConditions, finalTitle;
    protected List<String> selectedTitles, selectedConditions, expandedNodes;

    public ConditionTreeView() {
        finalConditions = null;
        finalTitle = "";
        selectedTitles = new ArrayList();
        selectedConditions = new ArrayList();
        expandedNodes = new ArrayList();

        setCellFactory(p -> new CheckBoxTreeCell<ConditionNode>() {
            @Override
            public void updateItem(ConditionNode item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }
                setText(item.getText());
                Node node = getGraphic();
                if (node != null && node instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) node;
                    if (item.getCondition() != null && !item.getCondition().isBlank()) {
                        FxmlControl.setTooltip(checkBox, item.getTitle() + "\n" + item.getCondition());
                    } else {
                        FxmlControl.removeTooltip(checkBox);
                    }
                }
            }
        });
    }

    public void checkSelection() {
        try {
            finalConditions = null;
            finalTitle = "";
            selectedTitles.clear();
            if (getRoot() == null) {
                return;
            }
            checkSelection(getRoot());
        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    public void checkSelection(TreeItem<ConditionNode> item) {
        try {
            if (item == null || !(item instanceof CheckBoxTreeItem)) {
                return;
            }
            CheckBoxTreeItem<ConditionNode> citem = (CheckBoxTreeItem<ConditionNode>) item;
            ConditionNode node = item.getValue();
            if (citem.isSelected()) {
                if (finalConditions == null || finalConditions.isBlank()) {
                    if (node.getCondition() == null || node.getCondition().isBlank()) {
                        finalConditions = "";
                    } else {
                        finalConditions = " ( " + node.getCondition() + ") ";
                    }
                } else {
                    if (node.getCondition() != null && !node.getCondition().isBlank()) {
                        finalConditions += " OR ( " + node.getCondition() + " ) ";
                    }
                }

                if (finalTitle == null || finalTitle.isBlank()) {
                    finalTitle = "\"" + node.getTitle() + "\"";
                } else {
                    finalTitle += " + \"" + node.getTitle() + "\"";
                }
                if (!selectedTitles.contains(node.getTitle())) {
                    selectedTitles.add(node.getTitle());
                }
                return;
            } else if (item.isLeaf() || !citem.isIndeterminate()) {
                return;
            }
            for (TreeItem<ConditionNode> child : item.getChildren()) {
                checkSelection(child);
            }
        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    public void setSelection() {
        if (selectedTitles == null || selectedTitles.isEmpty()
                || getRoot() == null) {
            return;
        }
        setSelection(getRoot());
    }

    public void setSelection(TreeItem<ConditionNode> item) {
        try {
            if (item == null || !(item instanceof CheckBoxTreeItem)) {
                return;
            }
            CheckBoxTreeItem<ConditionNode> citem = (CheckBoxTreeItem<ConditionNode>) item;
            ConditionNode node = item.getValue();
            if (selectedTitles.contains(node.getTitle())) {
                citem.setSelected(true);
                return;
            } else if (item.isLeaf()) {
                return;
            }
            for (TreeItem<ConditionNode> child : item.getChildren()) {
                setSelection(child);
            }
        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    public void selectAll() {
        CheckBoxTreeItem<ConditionNode> root = (CheckBoxTreeItem<ConditionNode>) getRoot();
        root.setSelected(true);
    }

    public void selectNone() {
        CheckBoxTreeItem<ConditionNode> root = (CheckBoxTreeItem<ConditionNode>) getRoot();
        root.setSelected(false);
    }

    public void checkExpanded() {
        try {
            expandedNodes.clear();
            if (getRoot() == null) {
                return;
            }
            checkExpanded(getRoot());
        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    public void checkExpanded(TreeItem<ConditionNode> item) {
        try {
            if (item == null || item.isLeaf() || !(item instanceof CheckBoxTreeItem)) {
                return;
            }
            CheckBoxTreeItem<ConditionNode> citem = (CheckBoxTreeItem<ConditionNode>) item;
            ConditionNode node = item.getValue();
            if (citem.isExpanded() && !expandedNodes.contains(node.getTitle())) {
                expandedNodes.add(node.getTitle());
            }
            for (TreeItem<ConditionNode> child : item.getChildren()) {
                checkExpanded(child);
            }
        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    public void check() {
        checkExpanded();
        checkSelection();
    }

    public void expandAll() {
        try {
            if (getRoot() == null) {
                return;
            }
            expandNode(getRoot());
        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    public void expandNode(TreeItem<ConditionNode> item) {
        try {
            if (item == null || item.isLeaf()) {
                return;
            }
            item.setExpanded(true);
            for (TreeItem<ConditionNode> child : item.getChildren()) {
                expandNode(child);
            }
        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    public void expandNone() {
        try {
            if (getRoot() == null) {
                return;
            }
            expandNone(getRoot());
        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    public void expandNone(TreeItem<ConditionNode> item) {
        try {
            if (item == null || item.isLeaf()) {
                return;
            }
            item.setExpanded(true);
            for (TreeItem<ConditionNode> child : item.getChildren()) {
                expandNode(child);
            }
        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }


    /*
        get/set
     */
    public String getFinalConditions() {
        return finalConditions;
    }

    public void setFinalConditions(String finalConditions) {
        this.finalConditions = finalConditions;
    }

    public String getFinalTitle() {
        return finalTitle;
    }

    public void setFinalTitle(String finalTitle) {
        this.finalTitle = finalTitle;
    }

    public List<String> getSelectedTitles() {
        return selectedTitles;
    }

    public void setSelectedTitles(List<String> selectedTitles) {
        this.selectedTitles = selectedTitles;
    }

    public List<String> getSelectedConditions() {
        return selectedConditions;
    }

    public void setSelectedConditions(List<String> selectedConditions) {
        this.selectedConditions = selectedConditions;
    }

    public List<String> getExpandedNodes() {
        return expandedNodes;
    }

    public void setExpandedNodes(List<String> expandedNodes) {
        this.expandedNodes = expandedNodes;
    }

}
