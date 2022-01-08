package com.example.wastify_codejod;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {
   private List<OnboardingItem> onboardingItems;

    public OnboardingAdapter(List<OnboardingItem> onboardingItems) {
        this.onboardingItems = onboardingItems;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_containing_onboarding,parent,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.setOnboardingData(onboardingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingItems.size();
    }

    class OnboardingViewHolder extends RecyclerView.ViewHolder{
        private TextView englishText;
        private TextView hindiText;
        private TextView gujaratiText;
        private ImageView imageOnboarding;

         OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            englishText =itemView.findViewById(R.id.engText);
            imageOnboarding=itemView.findViewById(R.id.imageOnboarding);

        }
        void setOnboardingData(OnboardingItem onboardingItem){
             englishText.setText(onboardingItem.getEnglishText());
             imageOnboarding.setImageResource(onboardingItem.getImage());

        }
    }
}
