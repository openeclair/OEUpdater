package openeclairupdaterapp.interfaces;
import openeclairupdaterapp.customTypes.UpdateInfo;
import openeclairupdaterapp.interfaces.IDownloadServiceCallback;

interface IDownloadService
{    
    void Download(in UpdateInfo ui);
    UpdateInfo getCurrentUpdate();
    String getCurrentMirrorName();
    boolean DownloadRunning();
    boolean PauseDownload();
    boolean cancelDownload();
    void registerCallback(in IDownloadServiceCallback cb);
    void unregisterCallback(in IDownloadServiceCallback cb);
}