package openeclairupdaterapp.interfaces;
import openeclairupdaterapp.customTypes.UpdateInfo;

interface IDownloadServiceCallback
{    
    void updateDownloadProgress(long downloaded, int total, String downloadedText, String speedText, String remainingTimeText);
    void UpdateDownloadMirror(String mirror);
    void DownloadFinished(in UpdateInfo u);
    void DownloadError();
}