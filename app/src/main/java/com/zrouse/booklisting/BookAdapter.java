package com.zrouse.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {



    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_listing, parent, false);
        }

        // Find the book at the given position in the list of books
        Book currentBook = getItem(position);

        // Find the TextView with view ID magnitude
        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        // Display the magnitude of the current earthquake in that TextView
        titleView.setText(currentBook.getTitle());

        // Find the TextView with view ID location
        TextView authorsView = (TextView) listItemView.findViewById(R.id.authors);
        // Display the location of the current earthquake in that TextView
        authorsView.setText(Arrays.toString(currentBook.getAuthor()));

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

}

