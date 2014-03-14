package com.engagepoint.model.questionnaire;


import com.engagepoint.model.question.Question;

import javax.inject.Inject;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represents group tag.
 */
public class GroupBean extends BasicBean
        implements Cloneable, BasicOperationWithBean {
    private static Long lastId = 1L;

    private Long id;
    private List<Question> questionsList = new ArrayList<Question>();
    private SectionBean sectionBean;

    public GroupBean() {
        id = lastId++;
    }

    public GroupBean(SectionBean sectionBean) {
        this.sectionBean = sectionBean;
        id = Long.valueOf(sectionBean.getId() + (lastId++).toString());
        sectionBean.addToInnerList(this);
    }

    public GroupBean(String groupName, SectionBean sectionBean) {
        this(sectionBean);
        setDisplayedName(groupName);
    }

    public GroupBean(String groupName, List<Question> questionsList, SectionBean sectionBean) {
        this(sectionBean);
        setDisplayedName(groupName);
        this.questionsList = questionsList;
    }

    @XmlElement(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SectionBean getSectionBean() {
        return sectionBean;
    }

    @XmlElementWrapper(name = "questions")
    @XmlElement(name = "question")
    public List<Question> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<Question> questionsList) {
        this.questionsList = questionsList;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        GroupBean copy = (GroupBean) super.clone();
        copy.setDisplayedName(this.getDisplayedName());
        List<Question> copyQuestionsList = null;
        if (questionsList != null) {
            copyQuestionsList = new ArrayList<Question>();
            for (Question questionBean : questionsList) {
                if (questionBean != null) {
                    copyQuestionsList.add((Question) questionBean.clone());
                } else {
                    copyQuestionsList.add(null);
                }
            }
        }
        copy.setQuestionsList(copyQuestionsList);
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GroupBean groupBean = (GroupBean) o;
        if (!id.equals(groupBean.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public void deleteFromInnerList(Object o) {
        questionsList.remove(o);
    }

    @Override
    public void addToInnerList(Object o) {
        questionsList.add((Question) o);
    }

    @Override
    public String toString() {
        return getDisplayedName();
    }

    @Override
    public String getType() {
        return "group";
    }

    @Override
    public String getDisplayedNodeType() {
        return "Group: ";
    }

    @Override
    public String getDisplayedId() {
        return " (ID: " + String.valueOf(this.id) + ") ";
    }
}

