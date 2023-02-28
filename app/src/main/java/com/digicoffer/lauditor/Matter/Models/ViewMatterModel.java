package com.digicoffer.lauditor.Matter.Models;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewMatterModel {
    String id;
    String caseNumber;
    String casetype;
    String courtName;
    String date_of_filling;
    String description;
    JSONArray clients;
    JSONArray documents;
    JSONArray groupAcls;
    JSONArray groups;
    JSONObject hearingDateDetails;
    boolean is_editable;
    String judges;
    String matterClosedDate;
    JSONArray members;
    String nextHearingDate;
    JSONArray opponentAdvocates;
    JSONObject owner;
    String priority;
    String status;
    JSONObject tags;
    JSONArray tempClients;
    JSONArray temporaryClients;
    String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public JSONArray getOpponentAdvocates() {
        return opponentAdvocates;
    }

    public void setOpponentAdvocates(JSONArray opponentAdvocates) {
        this.opponentAdvocates = opponentAdvocates;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getCasetype() {
        return casetype;
    }

    public void setCasetype(String casetype) {
        this.casetype = casetype;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getDate_of_filling() {
        return date_of_filling;
    }

    public void setDate_of_filling(String date_of_filling) {
        this.date_of_filling = date_of_filling;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JSONArray getClients() {
        return clients;
    }

    public void setClients(JSONArray clients) {
        this.clients = clients;
    }

    public JSONArray getDocuments() {
        return documents;
    }

    public void setDocuments(JSONArray documents) {
        this.documents = documents;
    }

    public JSONArray getGroupAcls() {
        return groupAcls;
    }

    public void setGroupAcls(JSONArray groupAcls) {
        this.groupAcls = groupAcls;
    }

    public JSONArray getGroups() {
        return groups;
    }

    public void setGroups(JSONArray groups) {
        this.groups = groups;
    }

    public JSONObject getHearingDateDetails() {
        return hearingDateDetails;
    }

    public void setHearingDateDetails(JSONObject hearingDateDetails) {
        this.hearingDateDetails = hearingDateDetails;
    }

    public boolean isIs_editable() {
        return is_editable;
    }

    public void setIs_editable(boolean is_editable) {
        this.is_editable = is_editable;
    }

    public String getJudges() {
        return judges;
    }

    public void setJudges(String judges) {
        this.judges = judges;
    }

    public String getMatterClosedDate() {
        return matterClosedDate;
    }

    public void setMatterClosedDate(String matterClosedDate) {
        this.matterClosedDate = matterClosedDate;
    }

    public JSONArray getMembers() {
        return members;
    }

    public void setMembers(JSONArray members) {
        this.members = members;
    }

    public String getNextHearingDate() {
        return nextHearingDate;
    }

    public void setNextHearingDate(String nextHearingDate) {
        this.nextHearingDate = nextHearingDate;
    }

    public JSONObject getOwner() {
        return owner;
    }

    public void setOwner(JSONObject owner) {
        this.owner = owner;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JSONObject getTags() {
        return tags;
    }

    public void setTags(JSONObject tags) {
        this.tags = tags;
    }

    public JSONArray getTempClients() {
        return tempClients;
    }

    public void setTempClients(JSONArray tempClients) {
        this.tempClients = tempClients;
    }

    public JSONArray getTemporaryClients() {
        return temporaryClients;
    }

    public void setTemporaryClients(JSONArray temporaryClients) {
        this.temporaryClients = temporaryClients;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
