package com.hunterdavis02.skilltrees_api.trees;

public class TreeCreateRequest {
    private String treeName;
    private String treeDescription;
    private String treeIconUrl;
    private String rootSkillName;
    private String rootSkillDescription;
    private String rootSkillIconUrl;

    public TreeCreateRequest() {
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public String getTreeDescription() {
        return treeDescription;
    }

    public void setTreeDescription(String treeDescription) {
        this.treeDescription = treeDescription;
    }

    public String getTreeIconUrl() {
        return treeIconUrl;
    }

    public void setTreeIconUrl(String treeIconUrl) {
        this.treeIconUrl = treeIconUrl;
    }

    public String getRootSkillName() {
        return rootSkillName;
    }

    public void setRootSkillName(String rootSkillName) {
        this.rootSkillName = rootSkillName;
    }

    public String getRootSkillDescription() {
        return rootSkillDescription;
    }

    public void setRootSkillDescription(String rootSkillDescription) {
        this.rootSkillDescription = rootSkillDescription;
    }

    public String getRootSkillIconUrl() {
        return rootSkillIconUrl;
    }

    public void setRootSkillIconUrl(String rootSkillIconUrl) {
        this.rootSkillIconUrl = rootSkillIconUrl;
    }
}
