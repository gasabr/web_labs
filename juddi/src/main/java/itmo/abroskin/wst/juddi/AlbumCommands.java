package itmo.abroskin.wst.juddi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import wst.abroskin.itmo.GetAlbumsRequest;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class AlbumCommands {
    @Autowired
    private AlbumClient client;

    @ShellMethod("search for album")
    public String beerSearch(
            @ShellOption(defaultValue = ShellOption.NULL) String name,
            @ShellOption(defaultValue = ShellOption.NULL) String author,
            @ShellOption(defaultValue = ShellOption.NULL) Integer billboardTop
    ) {
        GetAlbumsRequest request = new GetAlbumsRequest();
        request.setName(name);
        request.setAuthor(author);
        request.setBillboardDebut(billboardTop);
        List<String[]> tableData = client.getBeers(request)
                .getAlbums()
                .stream()
                .map(x -> new String[]{x.getName(), x.getAuthor(), x.getBillboardDebut().toString()})
                .collect(Collectors.toList());


        tableData.add(0, new String[]{
                "Name", "Author", "Charts Top"
        });


        ArrayTableModel arrayTableModel = new ArrayTableModel(tableData.toArray(new Object[0][0]));
        TableBuilder tableBuilder = new TableBuilder(arrayTableModel);
        tableBuilder.addHeaderAndVerticalsBorders(BorderStyle.fancy_light);
        return tableBuilder.build().render(200);
    }
}

