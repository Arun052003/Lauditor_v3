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
    String client_id;
    String client_type;
    String doc_id;
    String doc_type;
    String user_id;
    String member_id;
    String Email;
    String member_name;
    String closedate;
    String matterNumber;
    String matterType;
    String startdate;
    JSONArray timesheets;

    public JSONArray getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(JSONArray timesheets) {
        this.timesheets = timesheets;
    }

    public String getClosedate() {
        return closedate;
    }

    public void setClosedate(String closedate) {
        this.closedate = closedate;
    }

    public String getMatterNumber() {
        return matterNumber;
    }

    public void setMatterNumber(String matterNumber) {
        this.matterNumber = matterNumber;
    }

    public String getMatterType() {
        return matterType;
    }

    public void setMatterType(String matterType) {
        this.matterType = matterType;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    String Advocate_name;
    String Number;
    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    boolean canDelete;
    public String getAdvocate_name() {
        return Advocate_name;
    }

    public void setAdvocate_name(String advocate_name) {
        Advocate_name = advocate_name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getDoc_type() {
        return doc_type;
    }

    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_type() {
        return client_type;
    }

    public void setClient_type(String client_type) {
        this.client_type = client_type;
    }

    private boolean isSelected;
    boolean isChecked;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

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
    String group_id;

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    String group_name;

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
