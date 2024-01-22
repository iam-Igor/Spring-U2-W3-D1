package ygorgarofalo.SpringU2W3D1.reposiories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ygorgarofalo.SpringU2W3D1.entities.Device;


@Repository
public interface DeviceDAO extends JpaRepository<Device, Long> {
}
