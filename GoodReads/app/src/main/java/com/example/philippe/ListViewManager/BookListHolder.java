package com.example.philippe.ListViewManager;

import android.view.View;
import android.widget.TextView;

import com.example.philippe.goodreads.R;

/**
 * Created by Philippe_Travail on 2016-02-01.
 */
public class BookListHolder {
        private View view;
        private TextView title = null, authors = null, releaseYear = null, nbPages = null, description = null;
        public BookListHolder(View view) {
            this.view = view;
        }

        public TextView getTitle() {
            if (this.title == null) {

                this.title = (TextView) view.findViewById(R.id.title);
            }

            return this.title;
        }

        public TextView getAuthors() {
            if (this.authors == null) {
                this.authors = (TextView) view.findViewById(R.id.author);
            }
            return this.authors;
        }

        public TextView getReleaseYear() {
            if (this.releaseYear == null) {
                this.releaseYear = (TextView) view.findViewById(R.id.releaseYear);
            }
            return this.releaseYear;
        }

        public TextView getNbPages() {
            if (this.nbPages == null) {
                this.nbPages = (TextView) view.findViewById(R.id.nbPages);
            }
            return this.nbPages;
        }

        public TextView getDescription() {
            if (this.description == null) {
                this.description = (TextView) view.findViewById(R.id.description);
            }
            return this.description;
        }
    }
