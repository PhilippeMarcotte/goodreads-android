package com.example.philippe.ListViewManager;

import android.view.View;
import android.widget.TextView;

import com.example.philippe.goodreads.R;

/**
 * Created by Philippe_Travail on 2016-02-01.
 */
public class BookListHolder {
        private View view;
        private TextView upperText = null, lowerText = null;
        public BookListHolder(View view) {
            this.view = view;
        }

        public TextView getUpperText() {
            if (this.upperText == null) {

                this.upperText = (TextView) view.findViewById(R.id.nom);
            }

            return this.upperText;
        }

        public TextView getLowerText() {
            if (this.lowerText == null) {
                this.lowerText = (TextView) view.findViewById(R.id.author);
            }
            return this.lowerText;
        }
    }
