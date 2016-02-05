package com.example.philippe.ListViewManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;


import com.example.philippe.goodreads.R;
import com.example.philippe.goodreadsapi.Author;
import com.example.philippe.goodreadsapi.Book;

import java.util.ArrayList;

/**
 * Created by Philippe_Travail on 2016-02-01.
 */
public class BookListViewAdapter extends ArrayAdapter{
        public BookListViewAdapter(Context context, int textViewResourceId, ArrayList<Book> list) {
            super(context, textViewResourceId, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BookListHolder holder = null;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.book_item, null, false);
                holder = new BookListHolder(convertView);
                convertView.setTag(holder);
            }
            else {
                holder = (BookListHolder) convertView.getTag();
            }
            Book book = (Book)getItem(position);
            holder.getTitle().setText(book.getTitle());
            String authors = null;
            for (Author author:
                 book.getAuthors()) {
                authors += author.getName() + ", ";
            }
            authors = authors.substring(0, authors.length() - 2);
            holder.getAuthors().setText(authors);
            holder.getNbPages().setText(Integer.toString(book.getPages()));
            holder.getReleaseYear().setText(Integer.toString(book.getYearPublished()));
            holder.getDescription().setText(book.getDescription());
            // Resets the toolbar to be closed
            View descriptionBar = convertView.findViewById(R.id.descriptionBar);
            ((RelativeLayout.LayoutParams) descriptionBar.getLayoutParams()).bottomMargin = -50;
            descriptionBar.setVisibility(View.GONE);
            return convertView;
        }

    }
