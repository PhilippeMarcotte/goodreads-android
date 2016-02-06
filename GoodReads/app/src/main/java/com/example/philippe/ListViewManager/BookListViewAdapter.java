package com.example.philippe.ListViewManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.RelativeLayout;


import com.example.philippe.goodreads.R;
import com.example.philippe.goodreadsapi.Author;
import com.example.philippe.goodreadsapi.Book;
import com.example.philippe.goodreadsapi.Review;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philippe_Travail on 2016-02-01.
 */
public class BookListViewAdapter extends ArrayAdapter{
        private List<Review> items;
        private List<Review> filtered;
        private Filter filter;
        public BookListViewAdapter(Context context, int textViewResourceId, List<Review> list) {
            super(context, textViewResourceId, list);
            this.items = list;
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
            Review review = (Review)getItem(position);
            Book book = review.getBook();
            holder.getTitle().setText(book.getTitle());
            String authors = null;
            for (Author author:
                 book.getAuthors()) {
                if(authors != null)
                    authors += author.getName() + ", ";
                else
                    authors = author.getName() + ", ";

            }
            authors = authors.substring(0, authors.length() - 2);
            holder.getAuthors().setText(authors);
            holder.getNbPages().setText(Integer.toString(book.getPages()) + " pages");
            holder.getReleaseYear().setText(Integer.toString(book.getYearPublished()));
            holder.getDescription().setText(book.getDescription());
            String shelves = null;
            for (String shelf:
                    review.getShelves()) {
                if(shelves != null)
                    shelves += shelf + ", ";
                else
                    shelves = shelf + ", ";

            }
            shelves = shelves.substring(0, shelves.length() - 2);
            holder.getShelves().setText(shelves);
            // Resets the toolbar to be closed
            View descriptionBar = convertView.findViewById(R.id.descriptionBar);
            ((RelativeLayout.LayoutParams) descriptionBar.getLayoutParams()).bottomMargin = -50;
            descriptionBar.setVisibility(View.GONE);
            return convertView;
        }

    @Override
    public Filter getFilter()
    {
        if(filter == null)
            filter = new BookTitleFilter();
        return filter;
    }

    private class BookTitleFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // NOTE: this function is *always* called from a background thread, and
            // not the UI thread.
            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if(constraint != null && constraint.toString().length() > 0)
            {
                ArrayList<Review> filt = new ArrayList<>();

                for (Review review:
                     items) {
                if(review.getBook().getTitle().toLowerCase().contains(constraint))
                    filt.add(review);
                }
                result.count = filt.size();
                result.values = filt;
            }
            else
            {
                synchronized(this)
                {
                    result.values = items;
                    result.count = items.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // NOTE: this function is *always* called from the UI thread.
            filtered = (ArrayList<Review>)results.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = filtered.size(); i < l; i++)
                add(filtered.get(i));
            notifyDataSetInvalidated();
        }

    }

    }
