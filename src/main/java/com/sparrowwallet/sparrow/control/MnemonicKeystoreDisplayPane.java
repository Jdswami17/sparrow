package com.sparrowwallet.sparrow.control;

import com.sparrowwallet.drongo.wallet.DeterministicSeed;
import com.sparrowwallet.drongo.wallet.Keystore;
import com.sparrowwallet.drongo.wallet.WalletModel;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MnemonicKeystoreDisplayPane extends MnemonicKeystorePane {
    public MnemonicKeystoreDisplayPane(Keystore keystore) {
        super(keystore.getSeed().getType().getName(), keystore.getSeed().needsPassphrase() ? "Passphrase entered" : "No passphrase", "", "image/" + WalletModel.SEED.getType() + ".png");
        showHideLink.setVisible(false);
        buttonBox.getChildren().clear();

        showWordList(keystore.getSeed());
    }

    private void showWordList(DeterministicSeed seed) {
        List<String> words = seed.getMnemonicCode();
        setContent(getMnemonicWordsEntry(words.size()));
        setExpanded(true);

        for(int i = 0; i < wordsPane.getChildren().size(); i++) {
            WordEntry wordEntry = (WordEntry)wordsPane.getChildren().get(i);
            wordEntry.getEditor().setText(words.get(i));
            wordEntry.getEditor().setEditable(false);
        }
    }

    protected Node getMnemonicWordsEntry(int numWords) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);

        wordsPane = new TilePane();
        wordsPane.setPrefRows(numWords / 3);
        wordsPane.setHgap(10);
        wordsPane.setVgap(10);
        wordsPane.setOrientation(Orientation.VERTICAL);

        List<String> words = new ArrayList<>();
        for(int i = 0; i < numWords; i++) {
            words.add("");
        }

        ObservableList<String> wordEntryList = FXCollections.observableArrayList(words);
        wordEntriesProperty = new SimpleListProperty<>(wordEntryList);
        List<WordEntry> wordEntries = new ArrayList<>(numWords);
        for(int i = 0; i < numWords; i++) {
            wordEntries.add(new WordEntry(i, wordEntryList));
        }
        for(int i = 0; i < numWords - 1; i++) {
            wordEntries.get(i).setNextEntry(wordEntries.get(i + 1));
            wordEntries.get(i).setNextField(wordEntries.get(i + 1).getEditor());
        }
        wordsPane.getChildren().addAll(wordEntries);

        vBox.getChildren().add(wordsPane);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(vBox);
        return stackPane;
    }
}