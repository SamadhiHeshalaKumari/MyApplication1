package com.example.myapplication1;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.ProductViewHolder> {

        private Context mtx;
        private List<pizza> productList;
        private OnItemClickListner mListner;

        public interface OnItemClickListner{
            void  onItemClick(int position);
        }

        public void setOnItemCliclListener(OnItemClickListner listener){
            mListner = listener;
        }

        public PizzaAdapter(Context mtx, List<pizza> productList) {
            this.mtx = mtx;
            this.productList = productList;
        }

        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mtx);
            View view = inflater.inflate(R.layout.activity_main, null);

            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductViewHolder holder, int position) {
            pizza product = productList.get(position);

            String PizzaName = product.getName();
            Double PizzaPrice = product.getPrice();
            String PizzaDescription = product.getDescription();
            String PizzaImage = product.getImgurl();

            holder.textViewname.setText(PizzaName);
            holder.textViewprice.setText("Rs." + PizzaPrice);
            holder.textViewdescription.setText(PizzaDescription);

            Glide.with(mtx).load(PizzaImage).into(holder.imageView);

        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        public class ProductViewHolder extends RecyclerView.ViewHolder{

            public TextView textViewname, textViewdescription, textViewprice;
            public ImageView imageView;

            public ProductViewHolder(View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.image);
                textViewname = itemView.findViewById(R.id.name);
                textViewdescription = itemView.findViewById(R.id.description);
                textViewprice = itemView.findViewById(R.id.price);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mListner != null){
                            int position = getAdapterPosition();
                            if(position != RecyclerView.NO_POSITION){
                                mListner.onItemClick(position);
                            }
                        }
                    }
                });
            }
        }
    }


