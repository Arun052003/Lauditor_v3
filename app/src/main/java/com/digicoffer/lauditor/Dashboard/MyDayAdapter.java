package com.digicoffer.lauditor.Dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.DahboardModels.MydayModels.ClientChatModel;
import com.digicoffer.lauditor.DahboardModels.MydayModels.EmailModel;
import com.digicoffer.lauditor.DahboardModels.Item;
import com.digicoffer.lauditor.DahboardModels.MydayModels.MeetingModel;
import com.digicoffer.lauditor.DahboardModels.MydayModels.RelationshipRequestModel;
import com.digicoffer.lauditor.DahboardModels.MydayModels.TeamChatModel;
import com.digicoffer.lauditor.R;

import java.util.ArrayList;

public class MyDayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Item> items;

    public MyDayAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
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
}
