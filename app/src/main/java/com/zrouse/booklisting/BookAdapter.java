package com.zrouse.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_listing, parent, false);
        }

        // Find the book at the given position in the list of books
        Book currentBook = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        titleView.setText(currentBook.getTitle());

        TextView authorsView = (TextView) listItemView.findViewById(R.id.authors);
        authorsView.setText(Arrays.toString(currentBook.getAuthor()).replaceAll("\\[|\\]|null", ""));
        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
}

