package pack3.taskJ;

import java.util.*;
public class TaskJ {
    public static List<UpdatePart> dataParts = new ArrayList<>();
    public static int currentTimeSlot = 1;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt(),
                k = in.nextInt();

        for(int i = 0; i < k; i++)
            dataParts.add(new UpdatePart(i, 1));

        Device[] devices = new Device[n];
        for(int i = 0; i < n; i++)
            devices[i] = new Device(i, k);
        downloadFromInternet(devices[0]);

        while(!isDownloadDone(devices)){
            for(var d : devices)
                d.sendRequestToDownloadMissingPart(devices);

            for(var d : devices)
                d.executeRequest();

            Device.execute();

            for(var d : devices)
                d.tryDownload();

            currentTimeSlot++;
        }

        System.out.println(String.join(" ", Arrays.stream(Arrays.copyOfRange(devices, 1, devices.length))
                .sorted(Comparator.comparing(Device::getId))
                .map(x -> String.valueOf(x.finishingDownloadTimeSlot))
                .toList()));
    }

    public static void downloadFromInternet(Device device){
        device.downloadedParts.addAll(device.notLoadedParts);
        device.notLoadedParts.clear();
        device.finishingDownloadTimeSlot = 0;
    }

    public static boolean isDownloadDone(Device[] devices){
        return Arrays.stream(devices)
                .allMatch(Device::isDownloadEnd);
    }

    public static class Device {
        private final int id;
        private int finishingDownloadTimeSlot = -1;

        private final Set<UpdatePart> downloadedParts = new HashSet<>();
        private final Set<UpdatePart> notLoadedParts = new HashSet<>();
        private final Map<Device, Integer> trustedDevices = new HashMap<>();
        private final Map<Device, UpdatePart> requests = new HashMap<>();

        public int getId(){
            return id;
        }

        public Device(int id, int k){
            this.id = id;
            for(var i = 0; i < k; i++)
                notLoadedParts.add(dataParts.get(i));
        }

        public boolean isDownloadEnd(){
            return finishingDownloadTimeSlot != -1;
        }

        public int downloadedPartsCount(){
            return downloadedParts.size();
        }

        public void sendRequestToDownloadMissingPart(Device[] devices){
            if (isDownloadEnd())
                return;

            var requiredPart = notLoadedParts.stream()
                    .min(Comparator.comparing(UpdatePart::getDownloadedTimes)
                            .thenComparing(UpdatePart::getId))
                    .orElseThrow();

            Arrays.stream(devices)
                    .filter(x -> x.downloadedParts.contains(requiredPart))
                    .min(Comparator.comparing(Device::downloadedPartsCount)
                            .thenComparing(Device::getId))
                    .ifPresent(d -> d.sendRequest(this, requiredPart));
        }

        public void sendRequest(Device from, UpdatePart uploadPart){
            requests.put(from, uploadPart);
        }

        public static Device currentProcessingDevice;
        public int getTrust(){
            return currentProcessingDevice.trustedDevices.getOrDefault(this, 0);
        }

        public void executeRequest(){
            currentProcessingDevice = this;
            requests.keySet().stream()
                    .min(Comparator.comparing(Device::getTrust, Comparator.reverseOrder())
                            .thenComparing(Device::downloadedPartsCount)
                            .thenComparing(Device::getId))
                    .ifPresent(d -> queue.add(new Request(this, d, requests.get(d))));
            requests.clear();
        }

        public static List<Request> queue = new ArrayList<>();
        public static void execute(){
            for(var q : queue){
                q.to.downloadedParts.add(q.data);
                q.to.notLoadedParts.remove(q.data);
                q.data.downloadedTimes++;

                if (q.to.trustedDevices.containsKey(q.from))
                    q.to.trustedDevices.merge(q.from, 1, Integer::sum);
                else
                    q.to.trustedDevices.put(q.from, 1);
            }
            queue.clear();
        }

        public void tryDownload(){
            if (isDownloadEnd())
                return;

            if (notLoadedParts.isEmpty())
                finishingDownloadTimeSlot = currentTimeSlot;
        }
    }

    public static class UpdatePart implements Comparable<UpdatePart>{
        public final int id;
        public int downloadedTimes;

        public int getId(){
            return id;
        }

        public int getDownloadedTimes(){
            return downloadedTimes;
        }

        public UpdatePart(int id, int downloadedTimes){
            this.id = id;
            this.downloadedTimes = downloadedTimes;
        }

        @Override
        public int compareTo(UpdatePart o) {
            return id - o.id;
        }
    }

    public record Request(Device from, Device to, UpdatePart data){ }
}
