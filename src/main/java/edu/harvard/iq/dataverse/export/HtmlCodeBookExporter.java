package edu.harvard.iq.dataverse.export;

import com.google.auto.service.AutoService;
import edu.harvard.iq.dataverse.DatasetVersion;
import edu.harvard.iq.dataverse.export.ddi.DdiExportUtil;
import edu.harvard.iq.dataverse.export.spi.Exporter;
import edu.harvard.iq.dataverse.util.BundleUtil;

import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.OutputStream;

@AutoService(Exporter.class)
public class HtmlCodeBookExporter implements Exporter {

    @Override
    public String getProviderName() {
        return "html";
    }

    @Override
    public String getDisplayName() {
        return  BundleUtil.getStringFromBundle("dataset.exportBtn.itemLabel.html") != null ? BundleUtil.getStringFromBundle("dataset.exportBtn.itemLabel.html") : "DDI html codebook";
    }

    @Override
    public void exportDataset(DatasetVersion version, JsonObject json, OutputStream outputStream) throws ExportException {
        try {

            DdiExportUtil.datasetHtmlDDI(json, version, outputStream);
        } catch (XMLStreamException xse) {
            throw new ExportException ("Caught XMLStreamException performing DDI export");
        }
    }

    @Override
    public Boolean isXMLFormat() {
        return false;
    }

    @Override
    public Boolean isHarvestable() {
        // No, we don't want this format to be harvested!
        // For datasets with tabular data the <data> portions of the DDIs
        // become huge and expensive to parse; even as they don't contain any
        // metadata useful to remote harvesters. -- L.A. 4.5
        return false;
    }

    @Override
    public Boolean isAvailableToUsers() {
        return true;
    }

    @Override
    public String getXMLNameSpace() throws ExportException {
        return null;
    }

    @Override
    public String getXMLSchemaLocation() throws ExportException {
        return null;
    }

    @Override
    public String getXMLSchemaVersion() throws ExportException {
        return null;
    }

    @Override
    public void setParam(String name, Object value) {
        // this exporter does not uses or supports any parameters as of now.
    }

    @Override
    public String  getMediaType() {
        return MediaType.TEXT_HTML;
    };
}
