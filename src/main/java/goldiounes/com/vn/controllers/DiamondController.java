package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dto.DiamondDTO;
import goldiounes.com.vn.models.dto.DiamondDetailDTO;
import goldiounes.com.vn.models.entity.Diamond;
import goldiounes.com.vn.models.entity.DiamondDetail;
import goldiounes.com.vn.services.DiamondDetailService;
import goldiounes.com.vn.services.DiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diamond-management")
public class DiamondController {

    @Autowired
    private DiamondService diamondService;

    @Autowired
    private DiamondDetailService diamondDetailService;

    @PostMapping("/diamonds")
    public DiamondDTO createDiamond(@RequestBody DiamondDTO diamondDTO) {
        return diamondService.createDiamond(diamondDTO);
    }

    @GetMapping("/diamonds")
    public List<DiamondDTO> getAllDiamonds() {
        return diamondService.findAll();
    }

    @GetMapping("/diamonds/{id}")
    public DiamondDTO getDiamond(@PathVariable int id) {
        return diamondService.findById(id);
    }

    @PutMapping("/diamonds/{id}")
    public void updateDiamond(@PathVariable int id, @RequestBody DiamondDTO diamondDTO) {
        diamondService.updateDiamond(id, diamondDTO);
    }

    @DeleteMapping("/diamonds/{id}")
    public void deleteDiamond(@PathVariable int id) {
        diamondService.deleteDiamond(id);
    }

    @PostMapping("/diamonds/{diamondId}/details")
    public DiamondDetailDTO createDiamondDetail(@PathVariable int diamondId, @RequestBody DiamondDetailDTO diamondDetailDTO) {
        diamondDetailDTO.setDiamond(diamondService.findById(diamondId));
        return diamondDetailService.save(diamondDetailDTO);
    }

    @GetMapping("/diamonds/{diamondId}/details")
    public List<DiamondDetailDTO> getDiamondDetailsByDiamondId(@PathVariable int diamondId) {
        return diamondDetailService.findByDiamondId(diamondId);
    }

    @DeleteMapping("/diamonds/{diamondId}/details/{id}")
    public void deleteDiamondDetail(@PathVariable int id) {
        diamondDetailService.deleteById(id);
    }

    @PutMapping("/diamonds/{diamondId}/details/{id}")
    public DiamondDetailDTO updateDiamondDetail(@PathVariable int id, @RequestBody DiamondDetailDTO diamondDetailDTO) {
        return diamondDetailService.update(id, diamondDetailDTO);
    }
}
