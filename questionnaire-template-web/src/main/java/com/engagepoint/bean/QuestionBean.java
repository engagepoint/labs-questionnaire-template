package com.engagepoint.bean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.Random;

/**
 * Class represents question tag.
 */
@Named("question")
@RequestScoped
public class QuestionBean implements Cloneable, BasicBeanProperty {
    private Long id;					//id of the question
    private String questionText;		//questiontext
    private boolean requiredAnswer;		//is answer required or not
    private QuestionType questionType;	//questiontype from ENUM of questiontypes
    private String helpText;			//Help texts for questions
    
    private String defaultAnswer; 		//TODO temp, will be replaced after we implement hierarchy
    
    //Default values for questions
    //Dependent questions
    

    @XmlElement(name = "question-title")
    public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getHelpText() {
		return helpText;
	}

	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}

	@XmlAttribute(name = "question-id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDefaultAnswer() {
		return defaultAnswer;
	}

	public void setDefaultAnswer(String defaultAnswer) {
		this.defaultAnswer = defaultAnswer;
	}

	@XmlAttribute(required = true)
    public boolean isRequiredAnswer() {
        return requiredAnswer;
    }

    public void setRequiredAnswer(boolean requiredAnswer) {
        this.requiredAnswer = requiredAnswer;
    }

    @XmlElement(name = "question-type")
    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public QuestionBean() {
    }

    public QuestionBean(String questionText, boolean requiredAnswer, QuestionType questionType) {
        this.questionText = questionText;
        this.requiredAnswer = requiredAnswer;
        this.questionType = questionType;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        //Have not decided yet how to do id when cloning, now - random
        Random rand = new Random(60000);
        QuestionBean copy = (QuestionBean) super.clone();
        copy.setId(rand.nextLong());
        copy.setQuestionType(this.questionType);
        copy.setQuestionText(this.questionText);
        copy.setRequiredAnswer(this.requiredAnswer);
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionBean that = (QuestionBean) o;

        if (requiredAnswer != that.requiredAnswer) return false;

        if (questionText != null ? !questionText.equals(that.questionText) : that.questionText != null)
            return false;
        if (questionType != that.questionType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (questionText != null ? questionText.hashCode() : 0);
        result = 31 * result + (requiredAnswer ? 1 : 0);
        result = 31 * result + (questionType != null ? questionType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Question" + id;
    }

    @Override
    public String getType() {
        return "question";
    }
}
