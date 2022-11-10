package com.digicoffer.lauditor.Dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Dashboard.DahboardModels.MydayModels.ClientChatModel;
import com.digicoffer.lauditor.Dashboard.DahboardModels.MydayModels.EmailModel;
import com.digicoffer.lauditor.Dashboard.DahboardModels.Item;
import com.digicoffer.lauditor.Dashboard.DahboardModels.MydayModels.MeetingModel;
import com.digicoffer.lauditor.Dashboard.DahboardModels.MydayModels.RelationshipRequestModel;
import com.digicoffer.lauditor.Dashboard.DahboardModels.MydayModels.TeamChatModel;
import com.digicoffer.lauditor.Dashboard.DahboardModels.PracticeHeadModels.PracticeModel;
import com.digicoffer.lauditor.R;

import java.util.ArrayList;

public class MyDayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Item> items;
    String mTag;

    public MyDayAdapter(ArrayList<Item> items,String tag) {
        this.items = items;
        mTag = tag;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       if(mTag == "MyDay") {
           //0=meeting_card
           if (viewType == 0) {
               return new MeetingHolder(LayoutInflater.from(parent.getContext()).inflate(
                       R.layout.meeting_card,
                       parent,
                       false
               ));
           }//1=relationship_request_card
           else if (viewType == 1) {
               return new RelationshipRequestHolder(LayoutInflater.from(parent.getContext()).inflate(
                       R.layout.relationship_request_card,
                       parent,
                       false
               ));
           }//2=client_chat_card
           else if (viewType == 2) {
               return new ClientChatHolder(LayoutInflater.from(parent.getContext()).inflate(
                       R.layout.client_chat_card,
                       parent,
                       false
               ));
           }//3=team_chat_card
           else if (viewType == 3) {
               return new TeamChatHolder(LayoutInflater.from(parent.getContext()).inflate(
                       R.layout.team_chat_card,
                       parent,
                       false
               ));
           }//4=email_card
           else {
               return new EmailHolder(LayoutInflater.from(parent.getContext()).inflate(
                       R.layout.email_card,
                       parent,
                       false
               ));
           }
       }else if(mTag == "PH_KPI"){
           if (viewType==0){
               return new TBHholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.practice_head_tbh,parent,false)
               );
           }else  if (viewType==1){
               return new NBHholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.practice_head_nbh,parent,false)
               );
           }
           else  if (viewType==2){
               return new ARholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.practice_head_approximate_revenue,parent,false)
               );
           }
           else  if (viewType==3){
               return new ABRholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.practice_head_average_billing_rate,parent,false)
               );
           }
           else  if (viewType==4){
               return new TimeSheetholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.practice_head_timesheet_card,parent,false)
               );
           }
           else  if (viewType==5){
               return new Matterholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.practice_head_matters,parent,false)
               );
           }
           else  if (viewType==6){
               return new USholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.practice_head_used_storage_limit,parent,false)
               );
           }
           else  {
               return new CRholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.practice_head_client_relationships,parent,false)
               );
           }

       }else if (mTag == "SU_KPI"){
           if (viewType==0){
               return new PracticeHeadKPIAdapter.TBHholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.practice_head_tbh,parent,false)
               );
           }else  if (viewType==1){
               return new PracticeHeadKPIAdapter.NBHholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.practice_head_nbh,parent,false)
               );
           }
           else  if (viewType==2){
               return new ARholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.practice_head_approximate_revenue,parent,false)
               );
           }
           else  if (viewType==3){
               return new ABRholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.practice_head_average_billing_rate,parent,false)
               );
           }
           else  if (viewType==4){
               return new TimeSheetholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.super_user_matter_kpi,parent,false)
               );
           }
           else  if (viewType==5){
               return new Matterholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.super_user_kpi_new_client,parent,false)
               );
           }
           else  if (viewType==6){
               return new USholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.super_user_kpi_new_hire,parent,false)
               );
           }
           else  {
               return new CRholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.super_user_kpi_data_storage,parent,false)
               );
           }
       }else if(mTag == "TM_KPI"){
           if (viewType==0){
               return new PracticeHeadKPIAdapter.TBHholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.practice_head_tbh,parent,false)
               );
           }else  if (viewType==1){
               return new NBHholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.practice_head_nbh,parent,false)
               );
           }
           else  if (viewType==2){
               return new ARholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.tm_submitted_timesheet,parent,false)
               );
           }
           else  if (viewType==3){
               return new ABRholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.tm_pending_time_sheet,parent,false)
               );
           }
           else {
               return new TimeSheetholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.tm_my_active_matters,parent,false)
               );
           }
       }else{
           if (viewType==0){
               return new AdminKPIAdapter.NOGHolder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.admin_no_of_groups,parent,false)
               );
           }else  if (viewType==1){
               return new AdminKPIAdapter.TMholder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.admin_total_team_members,parent,false)
               );
           }
           else  if (viewType==2){
               return new AdminKPIAdapter.NewHiresHolder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.super_user_kpi_new_hire,parent,false)
               );
           }
           else  if (viewType==3){
               return new AdminKPIAdapter.Data_storageHolder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.super_user_kpi_data_storage,parent,false)
               );
           }
           else {
               return new AdminKPIAdapter.ProductSubcriptionHolder(
                       LayoutInflater.from(parent.getContext()).
                               inflate(R.layout.admin_product_subscription,parent,false)
               );
           }
       }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if(mTag=="MyDay") {
                if (getItemViewType(position) == 0) {
                    MeetingModel meetingModel = (MeetingModel) items.get(position).getObject();
                    ((MeetingHolder) holder).setMeetingData(meetingModel);
                } else if (getItemViewType(position) == 1) {
                    RelationshipRequestModel requestModel = (RelationshipRequestModel) items.get(position).getObject();
                    ((RelationshipRequestHolder) holder).RequestsData(requestModel);
                } else if (getItemViewType(position) == 2) {
                    ClientChatModel clientChatModel = (ClientChatModel) items.get(position).getObject();
                    ((ClientChatHolder) holder).ClienChatData(clientChatModel);
                } else if (getItemViewType(position) == 3) {
                    TeamChatModel teamChatModel = (TeamChatModel) items.get(position).getObject();
                    ((TeamChatHolder) holder).TeamchatData(teamChatModel);
                } else {
                    EmailModel emailModel = (EmailModel) items.get(position).getObject();
                    ((EmailHolder) holder).EmailData(emailModel);
                }
            }else if(mTag == "PH_KPI"){

                if (getItemViewType(position) == 0) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((TBHholder) holder).TBHdata(meetingModel);
                } else if (getItemViewType(position) == 1) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((NBHholder) holder).NBHdata(meetingModel);
                } else if (getItemViewType(position) == 2) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((ARholder) holder).ARdata(meetingModel);
                } else if (getItemViewType(position) == 3) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((ABRholder) holder).ABRdata(meetingModel);
                } else if (getItemViewType(position) == 4) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((TimeSheetholder) holder).TimeSheetdata(meetingModel);
                } else if (getItemViewType(position) == 5) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((Matterholder) holder).Mattersdata(meetingModel);
                } else if (getItemViewType(position) == 6) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((USholder) holder).USdata(meetingModel);
                } else {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((CRholder) holder).CRdata(meetingModel);
                }
            }else if(mTag == "SU_KPI"){
                if (getItemViewType(position) == 0) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((TBHholder) holder).TBHdata(meetingModel);
                } else if (getItemViewType(position) == 1) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((NBHholder) holder).NBHdata(meetingModel);
                } else if (getItemViewType(position) == 2) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((ARholder) holder).ARdata(meetingModel);
                } else if (getItemViewType(position) == 3) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((ABRholder) holder).ABRdata(meetingModel);
                } else if (getItemViewType(position) == 4) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((Matterholder) holder).Mattersdata(meetingModel);
                } else if (getItemViewType(position) == 5) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((NewClientsHolder) holder).setNewClientsdata(meetingModel);
                } else if (getItemViewType(position) == 6) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((NewHiresHolder) holder).setNewHiresdata(meetingModel);
                } else {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((Data_storageHolder) holder).setData_storageHolder(meetingModel);
                }
            }else if(mTag == "TM_KPI"){

                if (getItemViewType(position) == 0) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((TBHholder) holder).TBHdata(meetingModel);
                } else if (getItemViewType(position) == 1) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((NBHholder) holder).NBHdata(meetingModel);
                } else if (getItemViewType(position) == 2) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((SubmittedTimeSheetholder) holder).SubmittedTimeSheetdata(meetingModel);
                } else if (getItemViewType(position) == 3) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((PendingTimeSheetholder) holder).PTSdata(meetingModel);
                } else  {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((ActiveMatterHolder) holder).Mattersdata(meetingModel);
                }
            }else{
                if (getItemViewType(position) == 0) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((NOGHolder) holder).setNOGData(meetingModel);
                } else if (getItemViewType(position) == 1) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((TMholder) holder).setTMdata(meetingModel);
                } else if (getItemViewType(position) == 2) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((NewHiresHolder) holder).setNewHiresdata(meetingModel);
                } else if (getItemViewType(position) == 3) {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((Data_storageHolder) holder).setData_storageHolder(meetingModel);
                }  else {
                    PracticeModel meetingModel = (PracticeModel) items.get(position).getObject();
                    ((ProductSubcriptionHolder) holder).setProductSubcriptiondata(meetingModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    static class MeetingHolder extends RecyclerView.ViewHolder {

        private TextView tv_count, tv_time, tv_subject;

        public MeetingHolder(@NonNull View itemView) {
            super(itemView);
            tv_count = itemView.findViewById(R.id.tv_count_meeting);
            tv_time = itemView.findViewById(R.id.tv_time_meeting);
            tv_subject = itemView.findViewById(R.id.tv_subject_meeting);
        }

        void setMeetingData(MeetingModel meetingData) {
            tv_count.setText(meetingData.getCount());
            tv_time.setText(meetingData.getTime());
            tv_subject.setText(meetingData.getSubject());
        }
    }

    static class RelationshipRequestHolder extends RecyclerView.ViewHolder {
        private TextView tv_requested_counts, tv_client_name, tv_requested_time;

        public RelationshipRequestHolder(@NonNull View itemView) {
            super(itemView);
            tv_requested_counts = itemView.findViewById(R.id.tv_requests_count);
            tv_client_name = itemView.findViewById(R.id.tv_client_name);
            tv_requested_time = itemView.findViewById(R.id.tv_requested_time);
        }

        void RequestsData(RelationshipRequestModel requestModel) {
            tv_requested_counts.setText(requestModel.getCount());
            tv_client_name.setText(requestModel.getClient_name() + "" + "has accepted the relationship request");
            tv_requested_time.setText(requestModel.getTime());
        }
    }

    static class ClientChatHolder extends RecyclerView.ViewHolder {
        private TextView tv_client_count, tv_chat_time, tv_client_chat_name, tv_client_message;

        public ClientChatHolder(@NonNull View itemView) {
            super(itemView);
            tv_client_count = itemView.findViewById(R.id.tv_client_chat_count);
            tv_chat_time = itemView.findViewById(R.id.tv_client_chat_time);
            tv_client_chat_name = itemView.findViewById(R.id.tv_client_chat_name);
            tv_client_message = itemView.findViewById(R.id.tv_client_chat_message);
        }

        void ClienChatData(ClientChatModel clientChatModel) {
            tv_client_count.setText(clientChatModel.getCount());
            tv_client_chat_name.setText(clientChatModel.getClient_name());
            tv_chat_time.setText(clientChatModel.getTime());
            tv_client_message.setText(clientChatModel.getChat_message());
        }
    }

    static class TeamChatHolder extends RecyclerView.ViewHolder {

        private TextView tv_tm_count, tv_tm_time, tv_tm_name, tv_tm_message;

        public TeamChatHolder(@NonNull View itemView) {
            super(itemView);
            tv_tm_count = itemView.findViewById(R.id.tv_tm_count);
            tv_tm_name = itemView.findViewById(R.id.tv_tm_name);
            tv_tm_time = itemView.findViewById(R.id.tv_tm_time);
            tv_tm_message = itemView.findViewById(R.id.tv_tm_message);
        }

        void TeamchatData(TeamChatModel chatModel) {
            tv_tm_count.setText(chatModel.getCount());
            tv_tm_time.setText(chatModel.getTime());
            tv_tm_name.setText(chatModel.getTm_name());
            tv_tm_message.setText(chatModel.getTm_message());
        }
    }

    static class EmailHolder extends RecyclerView.ViewHolder {
        TextView tv_email_count, tv_email_time, tv_email_subject, tv_email_message;

        public EmailHolder(@NonNull View itemView) {
            super(itemView);
            tv_email_count = itemView.findViewById(R.id.tv_email_count);
            tv_email_message = itemView.findViewById(R.id.tv_email_message);
            tv_email_subject = itemView.findViewById(R.id.tv_email_subject);
            tv_email_time = itemView.findViewById(R.id.tv_email_time);
        }

        void EmailData(EmailModel emailModel) {
            tv_email_time.setText(emailModel.getEmail_time());
            tv_email_count.setText(emailModel.getCount());
            tv_email_subject.setText(emailModel.getEmail_subject());
            tv_email_message.setText(emailModel.getEmail_message());
        }
    }

    static class TBHholder extends RecyclerView.ViewHolder{
        private  TextView tv_billing_hours,tv_bh_percentage;
        public TBHholder(@NonNull View itemView) {
            super(itemView);
            tv_billing_hours = itemView.findViewById(R.id.tv_bhours);
            tv_bh_percentage = itemView.findViewById(R.id.tv_bh_percentage);
        }

        void TBHdata(PracticeModel practiceModel){
            tv_billing_hours.setText(practiceModel.getHours());
            tv_bh_percentage.setText(practiceModel.getPercentage());

        }

    }

    static class NBHholder extends RecyclerView.ViewHolder{
        private  TextView tv_non_billing_hours,tv_nbh_percentage;
        public NBHholder(@NonNull View itemView) {
            super(itemView);
            tv_non_billing_hours = itemView.findViewById(R.id.tv_nb_hours);
            tv_nbh_percentage = itemView.findViewById(R.id.tv_nbh_percentage);
        }

        void NBHdata(PracticeModel practiceModel){
            tv_non_billing_hours.setText(practiceModel.getHours());
            tv_non_billing_hours.setText(practiceModel.getPercentage());
        }
    }
    static class ARholder extends RecyclerView.ViewHolder{
        private TextView tv_revenue;
        public ARholder(@NonNull View itemView) {

            super(itemView);
            tv_revenue = itemView.findViewById(R.id.tv_revenue);
        }

        void ARdata(PracticeModel model){
            tv_revenue.setText(model.getHours());
        }
    }
    static class ABRholder extends RecyclerView.ViewHolder{
        private TextView tv_billing_rate;
        public ABRholder(@NonNull View itemView) {
            super(itemView);
            tv_billing_rate = itemView.findViewById(R.id.tv_billing_rate);
        }

        void ABRdata(PracticeModel practiceModel){
            tv_billing_rate.setText(practiceModel.getHours());
        }
    }
    static class TimeSheetholder extends RecyclerView.ViewHolder{
        private TextView tv_ttm,tv_nsm,tv_sm;
        public TimeSheetholder(@NonNull View itemView) {
            super(itemView);
            tv_ttm = itemView.findViewById(R.id.tv_total_team_members_count);
            tv_nsm = itemView.findViewById(R.id.tv_not_submitted_count);
            tv_sm = itemView.findViewById(R.id.tv_submitted_members_count);
        }
        void TimeSheetdata(PracticeModel practiceModel){

            tv_ttm.setText(practiceModel.getHours());
            tv_nsm.setText(practiceModel.getPercentage());
            tv_sm.setText(practiceModel.getSubmitted_members());
        }
    }
    static class Matterholder extends RecyclerView.ViewHolder{
        TextView tv_closed_members_count,tv_active_members_count;
        public Matterholder(@NonNull View itemView) {
            super(itemView);
            tv_closed_members_count = itemView.findViewById(R.id.tv_closed_members_count);
            tv_active_members_count = itemView.findViewById(R.id.tv_active_members_count);

        }

        void Mattersdata(PracticeModel practiceModel){
            tv_closed_members_count.setText(practiceModel.getHours());
            tv_active_members_count.setText(practiceModel.getPercentage());
        }
    }
    static class USholder extends RecyclerView.ViewHolder{
        private TextView tv_storage;
        public USholder(@NonNull View itemView) {
            super(itemView);
            tv_storage = itemView.findViewById(R.id.tv_storage);
        }

        void USdata(PracticeModel practiceModel){
            tv_storage.setText(practiceModel.getHours());
        }
    }
    static class CRholder extends RecyclerView.ViewHolder{
        private TextView tv_accepted_count,tv_pending_count,tv_deleted_count;
        public CRholder(@NonNull View itemView) {
            super(itemView);
            tv_accepted_count = itemView.findViewById(R.id.tv_accepted_count);
            tv_pending_count = itemView.findViewById(R.id.tv_pending_count);
            tv_deleted_count = itemView.findViewById(R.id.tv_delete_count);
        }

        void CRdata(PracticeModel practiceModel){
            tv_accepted_count.setText(practiceModel.getHours());
            tv_deleted_count.setText(practiceModel.getPercentage());
            tv_pending_count.setText(practiceModel.getSubmitted_members());
        }
    }
    static class NewClientsHolder extends RecyclerView.ViewHolder{
        private TextView tv_legal_count,tv_civil_count;
        public NewClientsHolder(@NonNull View itemView) {
            super(itemView);
            tv_legal_count = itemView.findViewById(R.id.tv_newclients_legal_count);
            tv_civil_count = itemView.findViewById(R.id.tv_newclients_civil_count);
        }

        void setNewClientsdata(PracticeModel practiceModel){
            tv_legal_count.setText(practiceModel.getHours());
            tv_civil_count.setText(practiceModel.getPercentage());
        }
    }
    static class NewHiresHolder extends RecyclerView.ViewHolder{
        private TextView tv_corporate_count,tv_criminal_count;
        public NewHiresHolder(@NonNull View itemView) {
            super(itemView);
            tv_corporate_count = itemView.findViewById(R.id.tv_newhire_corporate_count);
            tv_criminal_count = itemView.findViewById(R.id.tv_newhire_criminal_count);

        }

        void setNewHiresdata(PracticeModel practiceModel){
            tv_corporate_count.setText(practiceModel.getHours());
            tv_criminal_count.setText(practiceModel.getPercentage());

        }
    }
    static class Data_storageHolder extends RecyclerView.ViewHolder{
        private TextView tv_data_storage,tv_balance_storage;
        public Data_storageHolder(@NonNull View itemView) {
            super(itemView);
            tv_data_storage = itemView.findViewById(R.id.tv_sp_data_storage);
            tv_balance_storage = itemView.findViewById(R.id.tv_sp_balance_storage);

        }

        void setData_storageHolder(PracticeModel practiceModel){
            tv_data_storage.setText(practiceModel.getHours());
            tv_balance_storage.setText(practiceModel.getPercentage());

        }
    }
    static class SubmittedTimeSheetholder extends RecyclerView.ViewHolder{
        private TextView tv_from_date,tv_to_date;
        public SubmittedTimeSheetholder(@NonNull View itemView) {
            super(itemView);
            tv_from_date = itemView.findViewById(R.id.tv_from_date);
            tv_to_date = itemView.findViewById(R.id.tv_to_date);
        }
        void SubmittedTimeSheetdata(PracticeModel practiceModel){

            tv_from_date.setText(practiceModel.getHours());
            tv_to_date.setText(practiceModel.getPercentage());

        }
    }
    static class PendingTimeSheetholder extends RecyclerView.ViewHolder{
        private TextView tv_from_date,tv_to_date;
        public PendingTimeSheetholder(@NonNull View itemView) {
            super(itemView);
            tv_from_date = itemView.findViewById(R.id.tv_from_date);
            tv_to_date = itemView.findViewById(R.id.tv_to_date);
        }

        void PTSdata(PracticeModel practiceModel){
            tv_from_date.setText(practiceModel.getHours());
            tv_to_date.setText(practiceModel.getPercentage());
        }
    }
    static class ActiveMatterHolder extends RecyclerView.ViewHolder{
        private   TextView tv_general_count,tv_legal_count;
        public ActiveMatterHolder(@NonNull View itemView) {
            super(itemView);
            tv_general_count = itemView.findViewById(R.id.tv_general_count);
            tv_legal_count = itemView.findViewById(R.id.tv_legal_count);

        }

        void Mattersdata(PracticeModel practiceModel){
            tv_general_count.setText(practiceModel.getHours());
            tv_legal_count.setText(practiceModel.getPercentage());
        }
    }
    static class ProductSubcriptionHolder extends RecyclerView.ViewHolder{
        private TextView tv_ps_year;
        public ProductSubcriptionHolder(@NonNull View itemView) {
            super(itemView);
            tv_ps_year = itemView.findViewById(R.id.tv_ps_year);
        }
        void setProductSubcriptiondata(PracticeModel practiceModel){
            tv_ps_year.setText(practiceModel.getHours());
        }
    }
    static class NOGHolder extends RecyclerView.ViewHolder{
        private TextView tv_nog_count;
        public NOGHolder(@NonNull View itemView) {
            super(itemView);
            tv_nog_count = itemView.findViewById(R.id.tv_nog_count);

        }

        void setNOGData(PracticeModel practiceModel){
            tv_nog_count.setText(practiceModel.getHours());


        }

    }
    static class TMholder extends RecyclerView.ViewHolder{
        private  TextView tv_admin_tm_count;
        public TMholder(@NonNull View itemView) {
            super(itemView);
            tv_admin_tm_count = itemView.findViewById(R.id.tv_admin_tm_count);

        }

        void setTMdata(PracticeModel practiceModel){
            tv_admin_tm_count.setText(practiceModel.getHours());
        }
    }
}
