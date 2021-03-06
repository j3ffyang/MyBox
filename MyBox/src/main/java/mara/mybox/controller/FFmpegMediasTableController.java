package mara.mybox.controller;

import com.github.kokorin.jaffree.StreamType;
import com.github.kokorin.jaffree.ffprobe.FFprobe;
import com.github.kokorin.jaffree.ffprobe.FFprobeResult;
import com.github.kokorin.jaffree.ffprobe.Format;
import com.github.kokorin.jaffree.ffprobe.Stream;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.Modality;
import mara.mybox.data.MediaInformation;
import mara.mybox.db.TableMedia;
import mara.mybox.tools.DateTools;
import mara.mybox.tools.FileTools;
import static mara.mybox.value.AppVariables.logger;
import static mara.mybox.value.AppVariables.message;
import mara.mybox.value.CommonFxValues;

/**
 * @Author Mara
 * @CreateDate 2019-12-8
 * @Description
 * @License Apache License Version 2.0
 */
public class FFmpegMediasTableController extends MediaTableController {

    protected FFmpegBaseController parent;

    public FFmpegMediasTableController() {
        sourceExtensionFilter = CommonFxValues.FFmpegMediaExtensionFilter;
        targetExtensionFilter = sourceExtensionFilter;
    }

    @Override
    public void initializeNext() {
        try {

            examples = new ArrayList();
            examples.add("http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8");
            examples.add("http://download.oracle.com/otndocs/products/javafx/JavaRap/prog_index.m3u8");

        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    @Override
    protected void readMediaInfo(MediaInformation info) {
        try {
            parent = (FFmpegBaseController) parentController;

            if (info == null || info.getAddress() == null
                    || parent == null || parent.executable == null) {
                return;
            }
            synchronized (this) {
                if (task != null) {
                    return;
                }
                task = new SingletonTask<Void>() {

                    @Override
                    protected boolean handle() {
                        error = null;
                        try {
                            String address = info.getAddress();
                            if (info.getFile() != null) {
                                address = info.getFile().getAbsolutePath();
                            }
                            FFprobeResult probeResult
                                    = FFprobe.atPath(parent.executable.toPath().getParent())
                                            .setShowFormat(true).setShowStreams(true)
                                            .setInput(address)
                                            .execute();
                            Format format = probeResult.getFormat();
                            if (format == null) {
                                error = message("InvalidData");
                                return true;
                            }
                            StringBuilder s = new StringBuilder();
                            if (format.getDuration() != null) {
                                info.setDuration(Math.round(format.getDuration() * 1000));
                                s.append(message("Duration")).append(": ").append(
                                        DateTools.showSeconds(Math.round(format.getDuration()))).append("\n");
                            }
                            if (format.getSize() != null) {
                                info.setFileSize(format.getSize());
                                s.append(message("Size")).append(": ").append(FileTools.showFileSize(format.getSize())).append("\n");
                            }

                            List<Stream> streams = probeResult.getStreams();
                            if (streams != null) {
                                for (int i = 0; i < streams.size(); ++i) {
                                    Stream stream = streams.get(i);
                                    if (stream.getCodecType() == StreamType.VIDEO) {
                                        info.setVideoEncoding(stream.getCodecLongName());
                                        s.append(message("VideoEncoding")).append(": ").append(info.getVideoEncoding()).append("\n");
                                    } else if (stream.getCodecType() == StreamType.AUDIO) {
                                        info.setAudioEncoding(stream.getCodecLongName());
                                        s.append(message("AudioEncoding")).append(": ").append(info.getAudioEncoding()).append("\n");
                                    }
                                    if (stream.getWidth() != null && stream.getHeight() != null) {
                                        String resolution = stream.getWidth() + "x" + stream.getHeight();
                                        info.setWidth(stream.getWidth());
                                        info.setHeight(stream.getHeight());
                                        s.append(message("Resolution")).append(": ").append(resolution).append("\n");
                                    }
                                }
                            }
                            info.setInfo(s.toString());
                            makeHtml(info, null);

                            TableMedia.write(info);
                        } catch (Exception e) {
                            error = e.toString();
                        }
                        return true;
                    }

                    @Override
                    protected void whenSucceeded() {
                        if (error != null) {
                            popError(error);
                        }
                        tableView.refresh();
                        updateLabel();
                    }
                };
                parentController.openHandlingStage(task, Modality.WINDOW_MODAL,
                        message("ReadingMedia...") + "\n" + info.getAddress());
                Thread thread = new Thread(task);
                thread.setDaemon(true);
                thread.start();
            }

        } catch (Exception e) {
            popError(message("FailOpenMedia"));
            logger.debug(e.toString());
        }
    }

}
